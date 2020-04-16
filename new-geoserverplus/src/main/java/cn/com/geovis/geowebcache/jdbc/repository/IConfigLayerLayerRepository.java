package cn.com.geovis.geowebcache.jdbc.repository;

import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer;
import cn.com.geovis.geowebcache.jdbc.entity.ConfigLayer.StroageType;

import java.util.List;

public interface IConfigLayerLayerRepository {

	public List<ConfigLayer> findAllLayer();
	
	public int save(ConfigLayer layer);
	
	public ConfigLayer findByName(String layername);
	
	public ConfigLayer deleteByName(String layername);
	
	public List<ConfigLayer> findLayersByStroageType(StroageType stroageType);
}
