package cn.com.geovis.geowebcache.jdbc.rowmap;

import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.GridSet;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.MimeType;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.StroageType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ConfigLayerRowMapper implements RowMapper<ConfigLayer> {

	private Gson gson = new GsonBuilder().create();
	
	@Override
	public ConfigLayer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ConfigLayer configLayer = new ConfigLayer();
		
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String gridset = rs.getString("gridset");
		String mimeType = rs.getString("mimetype");
		double minX = rs.getDouble("minx");
		double minY = rs.getDouble("miny");
		double maxX = rs.getDouble("maxx");
		double maxY = rs.getDouble("maxy");
		int minLevel = rs.getInt("minlevel");
		int maxLevel = rs.getInt("maxlevel");
		String extentStr = rs.getString("extent");
		String stoageType = rs.getString("stroagetype");
						
		configLayer.setId(id);
		configLayer.setName(name);
		configLayer.setGridset(GridSet.fromString(gridset));
		configLayer.setMimeType(MimeType.fromString(mimeType));
		configLayer.setMinX(minX);
		configLayer.setMinY(minY);
		configLayer.setMaxX(maxX);
		configLayer.setMaxY(maxY);
		configLayer.setMinLevel(minLevel);
		configLayer.setMaxLevel(maxLevel);
		configLayer.setStroageType(StroageType.valueOf(stoageType));
		@SuppressWarnings("unchecked")
		Map<String, String> extent = gson.fromJson(extentStr, Map.class);
		configLayer.setExtent(extent);
		
		return configLayer;
	}

}
