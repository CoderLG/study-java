package cn.com.geovis.image.rest.param;

import com.thoughtworks.xstream.XStream;
import org.geoserver.config.util.XStreamPersister;
import org.geoserver.config.util.XStreamPersisterInitializer;
import org.geoserver.gwc.layer.GeoServerTileLayerInfo;
import org.geoserver.gwc.layer.GeoServerTileLayerInfoImpl;
import org.geowebcache.config.XMLGridSubset;

public class LayerParamsXStreamPersisterInitializer implements XStreamPersisterInitializer {

	@Override
	public void init(XStreamPersister persister) {
		XStream xs = persister.getXStream();
		xs.alias("layerParams", LayerParams.class);
		xs.alias("gridSubset", XMLGridSubset.class);
		xs.alias("tileLayer", TileLayerInfo.class);
		xs.addDefaultImplementation(GeoServerTileLayerInfoImpl.class, GeoServerTileLayerInfo.class);
		xs.allowTypes(new Class[] {LayerParams.class,XMLGridSubset.class});
	}

}
