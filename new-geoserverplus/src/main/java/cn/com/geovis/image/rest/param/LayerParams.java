package cn.com.geovis.image.rest.param;

import org.geoserver.catalog.CoverageInfo;
import org.geoserver.catalog.CoverageStoreInfo;
import org.geoserver.catalog.LayerGroupInfo;
import org.geoserver.gwc.layer.GeoServerTileLayerInfo;

public class LayerParams {
	
	private CoverageStoreInfo coverageStore;
	private CoverageInfo coverage;
	private GeoServerTileLayerInfo tileLayerInfo;
	private LayerGroupInfo layerGroupInfo;
	private String imageServiceUrl;
	private TileLayerInfo tileLayer;
	private String taskType;
	
	public LayerParams() {
		
	}
	
	public LayerParams(CoverageStoreInfo coverageStore, CoverageInfo coverage, GeoServerTileLayerInfo tileLayerInfo) {
		super();
		this.coverageStore = coverageStore;
		this.coverage = coverage;
		this.tileLayerInfo = tileLayerInfo;
	}
	
	public LayerParams(GeoServerTileLayerInfo tileLayerInfo,LayerGroupInfo layerGroupInfo) {
		super();
		this.tileLayerInfo = tileLayerInfo;
		this.layerGroupInfo = layerGroupInfo;
	}
	
	public CoverageStoreInfo getCoverageStore() {
		return coverageStore;
	}
	public void setCoverageStore(CoverageStoreInfo coverageStore) {
		this.coverageStore = coverageStore;
	}
	public CoverageInfo getCoverage() {
		return coverage;
	}
	public void setCoverage(CoverageInfo coverage) {
		this.coverage = coverage;
	}
	public GeoServerTileLayerInfo getTileLayerInfo() {
		return tileLayerInfo;
	}
	public void setTileLayerInfo(GeoServerTileLayerInfo tileLayerInfo) {
		this.tileLayerInfo = tileLayerInfo;
	}

	public LayerGroupInfo getLayerGroupInfo() {
		return layerGroupInfo;
	}

	public void setLayerGroupInfo(LayerGroupInfo layerGroupInfo) {
		this.layerGroupInfo = layerGroupInfo;
	}

	public String getImageServiceUrl() {
		return imageServiceUrl;
	}

	public void setImageServiceUrl(String imageServiceUrl) {
		this.imageServiceUrl = imageServiceUrl;
	}

	public TileLayerInfo getTileLayer() {
		return tileLayer;
	}

	public void setTileLayer(TileLayerInfo tileLayer) {
		this.tileLayer = tileLayer;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}
