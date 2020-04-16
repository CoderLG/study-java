package lg.dao.es;

import lg.domain.GFGX_Y_DMK_DMSJ;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DMSJDao extends ElasticsearchRepository<GFGX_Y_DMK_DMSJ,String> {

}
