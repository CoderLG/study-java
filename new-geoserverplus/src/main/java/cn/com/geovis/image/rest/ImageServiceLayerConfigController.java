package cn.com.geovis.image.rest;

import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.GridSet;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.MimeType;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.StroageType;
import cn.com.geovis.geowebcache.jdbc.repository.IConfigLayerLayerRepository;
import cn.com.geovis.image.seed.ImageTileBreeder;
import cn.com.geovis.image.rest.param.LayerParams;
import cn.com.geovis.image.rest.param.TileLayerInfo;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import lombok.extern.slf4j.Slf4j;
import org.geoserver.catalog.*;
import org.geoserver.gwc.ConfigurableBlobStore;
import org.geoserver.gwc.GWC;
import org.geoserver.gwc.layer.GeoServerTileLayer;
import org.geoserver.gwc.layer.GeoServerTileLayerInfo;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.rest.ResourceNotFoundException;
import org.geoserver.rest.RestException;
import org.geoserver.security.GeoServerSecurityManager;
import org.geowebcache.GeoWebCacheException;
import org.geowebcache.config.XMLGridSubset;
import org.geowebcache.grid.BoundingBox;
import org.geowebcache.grid.GridSetBroker;
import org.geowebcache.io.GeoWebCacheXStream;
import org.geowebcache.layer.TileLayer;
import org.geowebcache.layer.TileLayerDispatcher;
import org.geowebcache.seed.GWCTask;
import org.geowebcache.seed.SeedRequest;
import org.geowebcache.storage.blobstore.memory.CacheProvider;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.geoserver.gwc.GWC.tileLayerName;

@RestController
@ControllerAdvice
@Slf4j
//@RequestMapping(path = RestBaseController.ROOT_PATH + "/image/layer")
@RequestMapping("/rest/image/layer")
public class ImageServiceLayerConfigController {
	private Catalog catalog = null;

	private ImageTileBreeder imageTileBreeder;

	private static final String APPLICATION_TEXT = "application";
	private static final String UTF8_TEXT = "utf-8";
	private static final String EXIST_TEXT = "exists";

	@Autowired
	private IConfigLayerLayerRepository configLayerLayerRepository;

	//@Autowired
	private TileLayerDispatcher tld;

	//@Autowired
	//private GridSetBroker gridSetBroker;

	private Gson gson = new GsonBuilder().create();

	@Value("${stand-alone}")
	private boolean isStandAlone;

//	public ImageServiceLayerConfigController(Catalog catalog) {
//		this.catalog = catalog;
//	}

	@GetMapping(value = "/status")
	public ResponseEntity<String> layerStatus() {
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>("OK", headers, HttpStatus.OK);
	}

	@GetMapping("/seed/status")
	public ResponseEntity<String> getRunningLayerTasks(@RequestParam(required = false) String layer) {
		try {
			XStream xs = new GeoWebCacheXStream(new JsonHierarchicalStreamDriver());
			JSONObject obj = null;
			long[][] list;
			if (null == layer) {
				list = imageTileBreeder.getStatusList();
			} else {
				imageTileBreeder.findTileLayer(layer);
				list = imageTileBreeder.getStatusList(layer);
			}
			obj = new JSONObject(xs.toXML(list));

			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = new MediaType(APPLICATION_TEXT, "json", Charset.forName(UTF8_TEXT));
			headers.setContentType(mediaType);
			return new ResponseEntity<>(obj.toString(), headers, HttpStatus.OK);
		} catch (JSONException jse) {
			log.info(jse.getMessage());
			return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (GeoWebCacheException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/seed/{workspacename}")
	public ResponseEntity<String> seed(@RequestBody LayerParams layerParams, @PathVariable String workspacename)
			throws GeoWebCacheException {

		GeoServerTileLayerInfo tileLayerInfo = layerParams.getTileLayerInfo();

		String coverageName = tileLayerInfo.getName();
		String layername = workspacename + ":" + coverageName;
		// 创建成功，进行切片任务
		XMLGridSubset gridSubset = tileLayerInfo.getGridSubsets().iterator().next();
		String mimetype = tileLayerInfo.getMimeFormats().iterator().next();

		String taskType = layerParams.getTaskType();
		GWCTask.TYPE type = GWCTask.TYPE.SEED;
		if(!StringUtils.isEmpty(taskType)) {
			if(taskType.equals("RESEED")) {
				type = GWCTask.TYPE.RESEED;
			}else if(taskType.equals("TRUNCATE")) {
				type = GWCTask.TYPE.TRUNCATE;
			}
		}

		SeedRequest seedRequest = new SeedRequest(layername, null, gridSubset.getGridSetName(), 10,
				gridSubset.getZoomStart(), gridSubset.getZoomStop(), mimetype, type, null);

		String imageServiceUrl = layerParams.getImageServiceUrl();
		imageTileBreeder.seed(layername, seedRequest, imageServiceUrl);

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
		headers.setContentType(mediaType);
		return new ResponseEntity<>(coverageName, headers, HttpStatus.CREATED);
	}

	@PostMapping("/seed/kill/{workspacename}")
	public ResponseEntity<String> killSeedTask(@RequestBody LayerParams layerParams, @PathVariable String workspacename)
			throws GeoWebCacheException {

		GeoServerTileLayerInfo tileLayerInfo = layerParams.getTileLayerInfo();

		String coverageName = tileLayerInfo.getName();
		String layername = workspacename + ":" + coverageName;

		final TileLayer tileLayer = imageTileBreeder.findTileLayer(layername);
		if (tileLayer != null) {
			final Iterator<GWCTask> tasks = imageTileBreeder.getRunningAndPendingTasks();
			while (tasks.hasNext()) {
				GWCTask task = tasks.next();
				String layerName = task.getLayerName();
				if (tileLayer.getName().equals(layerName)) {
					long taskId = task.getTaskId();
					imageTileBreeder.terminateGWCTask(taskId);
				}
			}
		}

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
		headers.setContentType(mediaType);
		return new ResponseEntity<>(coverageName, headers, HttpStatus.CREATED);

	}

	@PostMapping("/seed/killAll")
	public ResponseEntity<String> killAllTask() {

		final Iterator<GWCTask> tasks = imageTileBreeder.getRunningAndPendingTasks();
		while (tasks.hasNext()) {
			GWCTask task = tasks.next();
			long taskId = task.getTaskId();
			imageTileBreeder.terminateGWCTask(taskId);
		}

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
		headers.setContentType(mediaType);
		return new ResponseEntity<>("OK", headers, HttpStatus.CREATED);

	}

	@PostMapping(value = "/publish/layer/{workspacename}")
	public ResponseEntity<String> layerPost(@RequestBody LayerParams layerParams, @PathVariable String workspacename)
			throws Exception {

		CoverageStoreInfo coverageStore = layerParams.getCoverageStore();
		CoverageInfo coverage = layerParams.getCoverage();
		GeoServerTileLayerInfo tileLayerInfo = layerParams.getTileLayerInfo();

		// 先检查是否存在同名的图层或者图层组
		if (checkLayerIsExists(workspacename, tileLayerInfo.getName())) {
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
			headers.setContentType(mediaType);
			return new ResponseEntity<>(EXIST_TEXT, headers, HttpStatus.OK);
		}

		StoreInfo storeInfo = catalog.getStoreByName(workspacename, coverageStore.getName(), StoreInfo.class);
		if (storeInfo == null) {
			// 尝试创建存储
			catalog.validate(coverageStore, true).throwIfInvalid();
			catalog.add(coverageStore);
		}

		// 然后尝试创建图层
		String coverageName = handleObjectPost(coverage, workspacename, coverageStore.getName());

		if (isStandAlone) {
			LayerInfo layer = catalog.getLayerByName(workspacename + ":" + coverageName);
			save(layer, tileLayerInfo);
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
			headers.setContentType(mediaType);
			return new ResponseEntity<>(layer.getName(), headers, HttpStatus.CREATED);
		}

		// 尝试一下删除图层
		final GWC gwc = GWC.get();
		gwc.removeTileLayers(Arrays.asList(workspacename + ":" + tileLayerInfo.getName()));

		// 没有错误 !! 向数据库里写入图层
		LayerInfo layer = catalog.getLayerByName(workspacename + ":" + coverageName);

		Map<String, String> extent = new HashMap<>();
		extent.put("publishId", layer.getId());

		XMLGridSubset xmlGridSubset = tileLayerInfo.getGridSubsets().stream().findFirst().orElse(new XMLGridSubset());
		String mimeType = tileLayerInfo.getMimeFormats().stream().findFirst().orElse("image/png");
		BoundingBox bbox = xmlGridSubset.getExtent();

		TileLayerInfo tileLayer = new TileLayerInfo();
		tileLayer.setWorkspace(workspacename);
		tileLayer.setLayerName(tileLayerInfo.getName());
		tileLayer.setGridset(xmlGridSubset.getGridSetName());
		tileLayer.setMimeType(mimeType);
		tileLayer.setMinX(bbox.getMinX());
		tileLayer.setMaxX(bbox.getMaxX());
		tileLayer.setMinY(bbox.getMinY());
		tileLayer.setMaxY(bbox.getMaxY());
		tileLayer.setMinLevel(xmlGridSubset.getZoomStart());
		tileLayer.setMaxLevel(xmlGridSubset.getZoomStop());
		tileLayer.setStroageType("WMS_HBASE");
		tileLayer.setExtent(gson.toJson(extent));

		LayerParams params = new LayerParams();
		params.setTileLayer(tileLayer);

		return publishCachelayer(params);

	}


	@SuppressWarnings("unchecked")
	@PostMapping(value = "/publish/cachelayer")
	public ResponseEntity<String> publishCachelayer(@RequestBody LayerParams layerParams) {

		TileLayerInfo tileLayer = layerParams.getTileLayer();
		ConfigLayer configLayer = new ConfigLayer();
		if (StringUtils.isEmpty(tileLayer.getWorkspace()))
			configLayer.setName(tileLayer.getLayerName());
		else
			configLayer.setName(tileLayer.getWorkspace() + ":" + tileLayer.getLayerName());

		configLayer.setGridset(GridSet.fromString(tileLayer.getGridset()));
		configLayer.setMimeType(MimeType.fromString(tileLayer.getMimeType()));
		configLayer.setMinX(tileLayer.getMinX());
		configLayer.setMinY(tileLayer.getMinY());
		configLayer.setMaxX(tileLayer.getMaxX());
		configLayer.setMaxY(tileLayer.getMaxY());
		configLayer.setMinLevel(tileLayer.getMinLevel());
		configLayer.setMaxLevel(tileLayer.getMaxLevel());
		configLayer.setStroageType(StroageType.valueOf(tileLayer.getStroageType()));
		configLayer.setExtent(gson.fromJson(tileLayer.getExtent(), Map.class));

		// 判断是什么图层
		TileLayer gwcTileLayer = null;

		/*switch (configLayer.getStroageType()) {
			case HBASE:
				gwcTileLayer = WMSHbaseLayerConfiguration.loadHBaseLayerFromConfigLayer(configLayer, gridSetBroker);
				break;
			case WMS_HBASE:
				gwcTileLayer = WMSHbaseLayerConfiguration.loadWMSHBaseLayerFromConfigLayer(configLayer, gridSetBroker,
						catalog);
				break;
			case MBTILE:
			case GV_IMAGE_MBTILE:
				gwcTileLayer = MBTileTileLayerConfiguration.loadTileLayer(configLayer, gridSetBroker);
				break;
			case TERRAIN:
			case GV_TERRAIN_MBTILE:
				gwcTileLayer = TerrainLayerConfiguration.loadTileLayer(configLayer, gridSetBroker);
				break;
			case OGCTWMS_HBASE:
				gwcTileLayer= OGCTHbaseConfiguration.loadTileLayer(configLayer, gridSetBroker);
				break;
			case OGCTWMS_MBTILE:
				gwcTileLayer= OGCTWmsMBTileConfig.loadTileLayer(configLayer, gridSetBroker);
				break;
			case ROCKS_TILE:
				gwcTileLayer = RocksdbTileLayerConfiguration.loadTileLayer(configLayer, gridSetBroker);
				break;
			case GV_TIME_ROCKS:
				gwcTileLayer = OGCTRocksdbConfiguration.loadTileLayer(configLayer, gridSetBroker);
				break;
			default:
				break;
		}
		if (gwcTileLayer != null) {
			gwcTileLayer.initialize(gridSetBroker);
			tld.addLayer(gwcTileLayer);
		}*/

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
		headers.setContentType(mediaType);
		return new ResponseEntity<>(tileLayer.getLayerName(), headers, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delete/cachelayer/{layername}")
	public ResponseEntity<String> deleteCacheLayers(@PathVariable String layername) {
		if (configLayerLayerRepository.deleteByName(layername) != null) {
			try {
				tld.removeLayer(layername);
			} catch (Exception e) {
				log.warn(e.getMessage());
			}
		}
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
		headers.setContentType(mediaType);
		return new ResponseEntity<>(layername, headers, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/delete/layer/{workspacename}/{layername}")
	public ResponseEntity<String> layerDelete(@PathVariable String workspacename, @PathVariable String layername) {

		String srclayername = layername;
		layername = workspacename + ":" + layername;
		LayerInfo layer = catalog.getLayerByName(layername);
		if (layer != null) {

			final GWC gwc = GWC.get();
			boolean tileLayerExists = gwc.hasTileLayer(layer);
			if (tileLayerExists) {
				gwc.removeTileLayers(Arrays.asList(layername));
			}

			deleteCacheLayers(layername);
			new CascadeDeleteVisitor(catalog).visit(layer);
			//删除coverage
			CoverageStoreInfo coverageStoreInfo = catalog.getCoverageStoreByName(workspacename,srclayername);
			if(coverageStoreInfo != null){
				String type = coverageStoreInfo.getType();
				if(!StringUtils.isEmpty(type) && !type.equalsIgnoreCase("PostGIS")) {
					catalog.remove(coverageStoreInfo);
				}
			}

		}

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>("success", headers, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "/delete/group/{workspacename}/{layername}")
	public ResponseEntity<String> layerGroupDelete(@PathVariable String workspacename, @PathVariable String layername) {

		LayerGroupInfo layerGroupInfo = catalog.getLayerGroupByName(workspacename, layername);
		if (layerGroupInfo != null) {
			final GWC gwc = GWC.get();
			boolean tileLayerExists = gwc.hasTileLayer(layerGroupInfo);
			if (tileLayerExists) {
				gwc.removeTileLayers(Arrays.asList(workspacename + ":" + layername));
			}
			catalog.remove(layerGroupInfo);
		}

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>("success", headers, HttpStatus.CREATED);

	}

	public boolean checkLayerIsExists(String workspacename, String layerName) {
		LayerGroupInfo layerGroupInfo = catalog.getLayerGroupByName(workspacename, layerName);
		LayerInfo layerInfo = null;
		if (StringUtils.isEmpty(workspacename)) {
			layerInfo = catalog.getLayerByName(layerName);
		} else {
			layerInfo = catalog.getLayerByName(workspacename + ":" + layerName);
		}

		return layerInfo != null || layerGroupInfo != null;

	}

	@PostMapping(value = "/publish/group/{workspacename}")
	public ResponseEntity<String> layerGroupPost(@RequestBody LayerParams layerParams,
                                                 @PathVariable String workspacename) throws Exception {

		LayerGroupInfo layerGroupInfo = layerParams.getLayerGroupInfo();
		GeoServerTileLayerInfo tileLayerInfo = layerParams.getTileLayerInfo();

		// 先检查是否存在同名的图层或者图层组
		if (checkLayerIsExists(workspacename, tileLayerInfo.getName())) {
			HttpHeaders headers = new HttpHeaders();
			MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
			headers.setContentType(mediaType);
			return new ResponseEntity<>(EXIST_TEXT, headers, HttpStatus.OK);
		}

		if (workspacename != null && catalog.getWorkspaceByName(workspacename) == null) {
			throw new ResourceNotFoundException("Workspace " + workspacename + " not found");
		}
		checkFullAdminRequired(workspacename);

		if (layerGroupInfo.getLayers().isEmpty()) {
			throw new RestException("layer group must not be empty", HttpStatus.BAD_REQUEST);
		}

		if (layerGroupInfo.getBounds() == null) {
            log.warn("Auto calculating layer group bounds");
			new CatalogBuilder(catalog).calculateLayerGroupBounds(layerGroupInfo);
		}

		if (workspacename != null) {
			layerGroupInfo.setWorkspace(catalog.getWorkspaceByName(workspacename));
		}

		if (layerGroupInfo.getMode() == null) {
			log.warn("Setting layer group mode SINGLE");
			layerGroupInfo.setMode(LayerGroupInfo.Mode.SINGLE);
		}

		if (workspacename != null) {
			LayerGroupInfo exists = catalog.getLayerGroupByName(workspacename, layerGroupInfo.getName());
			if (exists != null) {
				catalog.remove(exists);
			}
		}

		catalog.validate(layerGroupInfo, true).throwIfInvalid();

		catalog.add(layerGroupInfo);

		String layerGroupName = layerGroupInfo.getName();

		// 没有错误 !! 创建GWC缓存图层

		LayerGroupInfo layer = catalog.getLayerGroupByName(workspacename + ":" + layerGroupName);
		save(layer, tileLayerInfo);

		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType(APPLICATION_TEXT, "text", Charset.forName(UTF8_TEXT));
		headers.setContentType(mediaType);
		return new ResponseEntity<>(layer.getName(), headers, HttpStatus.CREATED);

	}

	public void save(CatalogInfo layer, GeoServerTileLayerInfo tileLayerInfo) {

		final GWC gwc = GWC.get();

		final boolean tileLayerExists = gwc.hasTileLayer(layer);

		// if we're creating a new layer, at this point the layer has already been
		// created and hence
		// has an id
		Preconditions.checkState(layer.getId() != null);
		tileLayerInfo.setId(layer.getId());

		final String name;
		final GridSetBroker gridsets = gwc.getGridSetBroker();
		GeoServerTileLayer tileLayer;
		if (layer instanceof LayerGroupInfo) {
			LayerGroupInfo groupInfo = (LayerGroupInfo) layer;
			name = tileLayerName(groupInfo);
			tileLayer = new GeoServerTileLayer(groupInfo, gridsets, tileLayerInfo);
		} else {
			LayerInfo layerInfo = (LayerInfo) layer;
			name = tileLayerName(layerInfo);
			tileLayer = new GeoServerTileLayer(layerInfo, gridsets, tileLayerInfo);
		}

		tileLayerInfo.setName(name);

		// Remove the Layer from the cache if it is present
		ConfigurableBlobStore store = GeoServerExtensions.bean(ConfigurableBlobStore.class);
		if (store != null) {
			CacheProvider cache = store.getCache();
			if (cache != null) {
				cache.removeUncachedLayer(name);
			}
		}

		if (tileLayerExists) {
			gwc.save(tileLayer);
		} else {
			gwc.add(tileLayer);
		}
	}

	/**
	 * Helper method that handles the POST of a coverage. This handles both the
	 * cases when the store is provided and when the store is not provided.
	 */
	private String handleObjectPost(CoverageInfo coverage, String workspace, String coverageStoreName)
			throws Exception {
		if (coverage.getStore() == null) {
			CoverageStoreInfo ds = catalog.getCoverageStoreByName(workspace, coverageStoreName);
			coverage.setStore(ds);
		}
		final boolean isNew = isNewCoverage(coverage);
		String nativeCoverageName = coverage.getNativeCoverageName();
		if (nativeCoverageName == null) {
			nativeCoverageName = coverage.getNativeName();
		}
		CatalogBuilder builder = new CatalogBuilder(catalog);
		CoverageStoreInfo store = coverage.getStore();
		builder.setStore(store);

		// We handle 2 different cases here
		if (!isNew) {
			// Configuring a partially defined coverage
			builder.initCoverage(coverage, nativeCoverageName);
		} else {
			// Configuring a brand new coverage (only name has been specified)
			String specifiedName = coverage.getName();
			coverage = builder.buildCoverageByName(nativeCoverageName, specifiedName);
		}

		NamespaceInfo ns = coverage.getNamespace();
		if (ns != null && !ns.getPrefix().equals(workspace)) {
			// from workspace
			ns = null;
		}

		if (ns == null) {
			// infer from workspace
			ns = catalog.getNamespaceByPrefix(workspace);
			coverage.setNamespace(ns);
		}

		coverage.setEnabled(true);
		catalog.validate(coverage, true).throwIfInvalid();
		catalog.add(coverage);

		// create a layer for the coverage
		catalog.add(builder.buildLayer(coverage));

		String infomsg = "POST coverage " + coverageStoreName + "," + coverage.getName();
		log.info(infomsg);
		return coverage.getName();
	}

	private boolean isNewCoverage(CoverageInfo coverage) {
		return coverage.getName() != null && (coverage.isAdvertised()) && (!coverage.isEnabled())
				&& (coverage.getAlias() == null) && (coverage.getCRS() == null)
				&& (coverage.getDefaultInterpolationMethod() == null) && (coverage.getDescription() == null)
				&& (coverage.getDimensions() == null) && (coverage.getGrid() == null)
				&& (coverage.getInterpolationMethods() == null) && (coverage.getKeywords() == null)
				&& (coverage.getLatLonBoundingBox() == null) && (coverage.getMetadata() == null)
				&& (coverage.getNativeBoundingBox() == null) && (coverage.getNativeCRS() == null)
				&& (coverage.getNativeFormat() == null) && (coverage.getProjectionPolicy() == null)
				&& (coverage.getSRS() == null) && (coverage.getResponseSRS() == null)
				&& (coverage.getRequestSRS() == null);
	}

	public ImageTileBreeder getImageTileBreeder() {
		return imageTileBreeder;
	}

	public void setImageTileBreeder(ImageTileBreeder imageTileBreeder) {
		this.imageTileBreeder = imageTileBreeder;
	}

	/**
	 * Determines if the current user is authenticated as full administrator.
	 */
	protected boolean isAuthenticatedAsAdmin() {
		return SecurityContextHolder.getContext() != null
				&& GeoServerExtensions.bean(GeoServerSecurityManager.class).checkAuthenticationForAdminRole();
	}

	/**
	 * Validates the current user can edit the resource (full admin required if
	 * workspaceName is null)
	 *
	 * @param workspaceName
	 */
	protected void checkFullAdminRequired(String workspaceName) {
		// global workspaces/styles can only be edited by a full admin
		if (workspaceName == null && !isAuthenticatedAsAdmin()) {
			throw new RestException("Cannot edit global resource , full admin credentials required",
					HttpStatus.METHOD_NOT_ALLOWED);
		}
	}

}
