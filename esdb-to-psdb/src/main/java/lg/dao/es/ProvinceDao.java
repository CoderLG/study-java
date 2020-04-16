package lg.dao.es;


import lg.domain.Province;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProvinceDao extends ElasticsearchRepository<Province,String> {


}
