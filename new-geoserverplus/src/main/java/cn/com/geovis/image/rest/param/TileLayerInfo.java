package cn.com.geovis.image.rest.param;

public class TileLayerInfo {
		
	private String layerName;

	private String mimeType;

	private Double minX;

	private Double minY;

	private Double maxX;

	private Double maxY;

	private Integer minLevel;

	private Integer maxLevel;

	private String gridset;

	private String workspace;
	
	private String stroageType;
	
	private String extent;

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Double getMinX() {
		return minX;
	}

	public void setMinX(Double minX) {
		this.minX = minX;
	}

	public Double getMinY() {
		return minY;
	}

	public void setMinY(Double minY) {
		this.minY = minY;
	}

	public Double getMaxX() {
		return maxX;
	}

	public void setMaxX(Double maxX) {
		this.maxX = maxX;
	}

	public Double getMaxY() {
		return maxY;
	}

	public void setMaxY(Double maxY) {
		this.maxY = maxY;
	}

	public Integer getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}

	public Integer getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}

	public String getGridset() {
		return gridset;
	}

	public void setGridset(String gridset) {
		this.gridset = gridset;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getStroageType() {
		return stroageType;
	}

	public void setStroageType(String stroageType) {
		this.stroageType = stroageType;
	}

	public String getExtent() {
		return extent;
	}

	public void setExtent(String extent) {
		this.extent = extent;
	}

	
	
}
