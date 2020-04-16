package lg.service.impl;

import lg.dao.place.PlaceDao;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.domain.ToponymData;
import lg.dvo.DMSJReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-11 16:17
 * desc:
 */
@Service
@Slf4j
public class PlaceServiceImpl {

    @Autowired
    private PlaceDao placeDao;


    public  List<ToponymData>  findToponymData(int lim, int off) {
        return placeDao.findToponymData(lim,off);
    }


    public  List<DMSJReq>  findSome(int lim, int off) {
       return placeDao.findSome(lim,off);
    }

    public  List<GFGX_Y_DMK_DMSJ_DAO>  find_GFGX_Y_DMK_DMSJ_Arr(int lim, int off) {
        return placeDao.findArr(lim,off);
    }
}
