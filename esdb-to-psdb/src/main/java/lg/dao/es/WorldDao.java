package lg.dao.es;

import lg.domain.World;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WorldDao extends ElasticsearchRepository<World,String> {
    List<World> findAllByName(String name);
}
