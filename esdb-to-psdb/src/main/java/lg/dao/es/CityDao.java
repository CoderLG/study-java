package lg.dao.es;

import lg.domain.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CityDao extends ElasticsearchRepository<City,String> {

    List<City> findAllByParent(String code);

}
