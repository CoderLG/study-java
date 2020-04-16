package cn.com.geovis.geowebcache.jdbc.entity;

import java.util.Map;

public class ConfigLayer {
	
	private long id;
	
	private String name;
	
	private GridSet gridset;
	
	private MimeType mimeType;
	
	private double minX;
	
	private double minY;
	
	private double maxX;
	
	private double maxY;
	
	private int minLevel;
	
	private int maxLevel;
	
	private StroageType stroageType;
	
	private Map<String, String> extent;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getMinX() {
		return minX;
	}
	public void setMinX(double minX) {
		this.minX = minX;
	}
	public double getMinY() {
		return minY;
	}
	public void setMinY(double minY) {
		this.minY = minY;
	}
	public double getMaxX() {
		return maxX;
	}
	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
	public int getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	public GridSet getGridset() {
		return gridset;
	}
	public void setGridset(GridSet gridset) {
		this.gridset = gridset;
	}
	public MimeType getMimeType() {
		return mimeType;
	}
	public void setMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
	}
	public Map<String, String> getExtent() {
		return extent;
	}
	public void setExtent(Map<String, String> extent) {
		this.extent = extent;
	}
	
	
	
	public StroageType getStroageType() {
		return stroageType;
	}
	public void setStroageType(StroageType stroageType) {
		this.stroageType = stroageType;
	}



	public enum StroageType{
		HBASE,WMS_HBASE,MBTILE,ARCGISCACHE,TERRAIN,GV_IMAGE_MBTILE,GV_TERRAIN_MBTILE,OGCTWMS_HBASE,OGCTWMS_MBTILE,ROCKS_TILE,GV_TIME_ROCKS;
	}
	
	public enum GridSet{
		
		EPSG4326,EPSG3857;
		
		public static GridSet fromString(String gridset) {
			if(gridset.equalsIgnoreCase("epsg:4326"))
				return EPSG4326;
			if(gridset.equalsIgnoreCase("epsg:3857"))
				return EPSG3857;
			return null;
		}
		
		@Override
		public String toString() {
			if(this == GridSet.EPSG4326)
				return "EPSG:4326";
			if(this == GridSet.EPSG3857)
				return "EPSG:3857";
			return super.toString();
		}
	}
	
	public enum MimeType{
		PNG,JPEG,TIF,TIFF,TERRAIN;
		
		public static MimeType fromString(String mimeType) {
			if(mimeType.equalsIgnoreCase("image/png"))
				return PNG;
			if(mimeType.equalsIgnoreCase("image/jpeg"))
				return JPEG;
			if(mimeType.equalsIgnoreCase("image/jpg"))
				return JPEG;
			if(mimeType.equalsIgnoreCase("image/tif"))
				return TIF;
			if(mimeType.equalsIgnoreCase("image/tiff"))
				return TIFF;
			if(mimeType.equalsIgnoreCase("image/terrain"))
				return TERRAIN;
			
			return null;
		}
		
		@Override
		public String toString() {
			if(this == PNG)
				return "image/png";
			if(this == JPEG)
				return "image/jpeg";
			if(this == TIF)
				return "image/tif";
			if(this == TIFF)
				return "image/tiff";
			if(this == TERRAIN)
				return "image/terrain";
			return super.toString();
		}
	}

}
