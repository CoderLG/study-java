package cn.com.geovis.image.seed.listener;

public interface ISeedTaskListener {

	public void taskStart(String layerName);
	
	public void taskSuccess(String layerName, long tilesTotal, long tilesDone);

	public void taskBeStop(String layerName, long tilesTotal, long tilesDone);
	
}
