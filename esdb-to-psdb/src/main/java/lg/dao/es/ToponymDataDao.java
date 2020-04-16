package lg.dao.es;


import lg.domain.ToponymData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ToponymDataDao extends ElasticsearchRepository<ToponymData,String> {

    Page<ToponymData> findAllByToponymLayerCoding(int code, Pageable pageable);

    List<ToponymData> findAllByToponym(String topnym);
}
