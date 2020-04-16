package lg.dao.es;

import lg.domain.County;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CountyDao extends ElasticsearchRepository<County,String> {
    List<County> findAllByParent(String code);
}
