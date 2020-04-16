package lg.dao.es;

import lg.domain.GFGX_Y_DMK_DMSJ;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LGDMSJDao extends ElasticsearchRepository<GFGX_Y_DMK_DMSJ_DAO,String> {

}
