package lg.controller;

import io.swagger.annotations.Api;
import lg.dao.place.PlaceDao;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.domain.ToponymData;
import lg.service.impl.PlaceServiceImpl;
import lg.dvo.DMSJReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-11 16:24
 * desc:
 */
@Api(tags = "地名数据库抽取")
@RestController
public class PlaceController {

    @Autowired
    private PlaceDao placeDao;

    @GetMapping("getToponymData")
    public List<ToponymData> getToponymData(@RequestParam int limit, @RequestParam int offset){
        List<ToponymData> some = placeDao.findToponymData(limit, offset);
        return some;
    }



    @Autowired
    private  PlaceServiceImpl placeService;

    @GetMapping("find_GFGX_Y_DMK_DMSJ_Arr")
    public List<GFGX_Y_DMK_DMSJ_DAO> find_GFGX_Y_DMK_DMSJ_Arr(@RequestParam int limit, @RequestParam int offset){
        List<GFGX_Y_DMK_DMSJ_DAO> some = placeService.find_GFGX_Y_DMK_DMSJ_Arr(limit, offset);
        return some;
    }

    @Deprecated
    @GetMapping("getSome")
    public List<DMSJReq> getSome(@RequestParam int limit, @RequestParam int offset){
        List<DMSJReq> some = placeService.findSome(limit, offset);
        return some;
    }


}
