package cn.com.geovis.geowebcache.jdbc.repository;

import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.StroageType;
import cn.com.geovis.geowebcache.jdbc.rowmap.ConfigLayerRowMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConfigLayerRepository implements IConfigLayerLayerRepository {

	private Gson gson = new GsonBuilder().create();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ConfigLayer> findAllLayer() {
		
		return jdbcTemplate.query("select * from configlayer", new ConfigLayerRowMapper());
	}

	@Override
	public int save(ConfigLayer layer) {
		
		if(findByName(layer.getName()) == null) {
			Object[] params = new Object[] {
					layer.getName(),layer.getGridset().toString(),layer.getMimeType().toString(),
					layer.getMinX(),layer.getMinY(),layer.getMaxX(),layer.getMaxY(),layer.getMinLevel(),layer.getMaxLevel(),
					layer.getStroageType().toString(),gson.toJson(layer.getExtent())
			};
			return jdbcTemplate.update("insert into "
					+ "configlayer(name,gridset,mimetype,minx,miny,maxx,maxy,minlevel,maxlevel,stroagetype,extent) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?)", params
					);
		}
		
		return 0;
	}

	@Override
	public ConfigLayer findByName(String layername) {

		List<ConfigLayer> configLayers = jdbcTemplate.query("select * from configlayer  where name = ? limit 1", new Object[] {layername},  new ConfigLayerRowMapper());
		if(!configLayers.isEmpty())
			return configLayers.get(0);
		return null;
	}

	@Override
	public ConfigLayer deleteByName(String layername) {

		ConfigLayer configLayer = findByName(layername);
		if(configLayer != null) {
			jdbcTemplate.update("delete from configlayer where name = ?", layername);
		}
		
		return configLayer;
	}

	@Override
	public List<ConfigLayer> findLayersByStroageType(StroageType stroageType) {

		return jdbcTemplate.query("select * from configlayer where stroagetype = ? ", new Object[] {stroageType.toString()},  new ConfigLayerRowMapper());
	}

}
