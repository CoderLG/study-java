package lg.controller;

import io.swagger.annotations.Api;
import lg.dao.es.ToponymDataDao;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.domain.ToponymData;
import lg.service.impl.ESServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-12 12:16
 * desc:
 */
@Api(tags = "ES数据库保存")
@RestController
public class ESController {



    @Autowired
    private  ToponymDataDao toponymDataDao;

    @GetMapping("getToponymDataCount")
    public Long getToponymDataCount(){
        return toponymDataDao.count();
    }

    @PostMapping("saveToponymDatas")
    public void saveToponymDatas(@RequestBody List<ToponymData> toponymData){
        toponymDataDao.saveAll(toponymData);
    }



    @Autowired
    private ESServiceImpl esService;

    @GetMapping("get_GFGX_Y_DMK_DMSJ_count")
    public Long get_GFGX_Y_DMK_DMSJ_count(){
        return esService.get_GFGX_Y_DMK_DMSJ_count();
    }

    @GetMapping("get_GFGX_Y_DMK_DMSJ_by_id")
    public GFGX_Y_DMK_DMSJ_DAO get_GFGX_Y_DMK_DMSJ_by_id(@RequestParam String id){
        return esService.get_GFGX_Y_DMK_DMSJ_by_id(id);
    }

    @PostMapping("save_GFGX_Y_DMK_DMSJ")
    public void save_GFGX_Y_DMK_DMSJ(@RequestBody GFGX_Y_DMK_DMSJ_DAO gfgx_y_dmk_dmsj_dao){
       esService.save_GFGX_Y_DMK_DMSJ(gfgx_y_dmk_dmsj_dao);
    }

    @PostMapping("save_GFGX_Y_DMK_DMSJ_Arr")
    public void save_GFGX_Y_DMK_DMSJ_Arr(@RequestBody List<GFGX_Y_DMK_DMSJ_DAO> gfgx_y_dmk_dmsj_daos){
        esService.hand_save_GFGX_Y_DMK_DMSJ_Arr(gfgx_y_dmk_dmsj_daos);
    }



}

