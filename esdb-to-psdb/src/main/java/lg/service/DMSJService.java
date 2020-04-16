package lg.service;


import lg.domain.GFGX_Y_DMK_DMSJ;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.dvo.DMSJReq;

import java.util.List;

public interface DMSJService {

    void save(DMSJReq req);
    void saveList(List<DMSJReq> req);
    void saveArr(List<GFGX_Y_DMK_DMSJ_DAO> arr);
    GFGX_Y_DMK_DMSJ get(String id) throws Exception;

    List<GFGX_Y_DMK_DMSJ> findAllByDLBM(String dlbm);

    List<GFGX_Y_DMK_DMSJ> findAllByFuzzyName(String placeName);
}
