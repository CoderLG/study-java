package geovis.services.controller;


import geovis.services.dao.GFGX_Y_DMK_DMSJ_Dao;
import geovis.services.domain.GFGX_Y_DMK_DMSJ;
import geovis.services.dto.XYZ;
import geovis.services.utils.TileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Morris
 * @date 15:49 2019/10/9
 */
@Api(tags = "修改老地名中的地理编码")
@RestController
@RequestMapping
public class DistrictController {

    @Autowired
    private GFGX_Y_DMK_DMSJ_Dao gfgx_y_dmk_dmsj_dao;

    @ApiOperation(value = "test", notes = "test")
    @PostMapping(value = "/test")
    public String test(){
        XYZ tileNumber = TileUtil.getTileNumber(-170.5, 80, 2);

        String res = tileNumber.getZ().toString();
        String y = tileNumber.getY().toString();
        String x = tileNumber.getX().toString();

        for(int i = 0; i<7-y.length();i++){
            res = res+"0";
        }
        res = res + y;

        for(int i = 0; i<7-x.length();i++){
            res = res+"0";
        }
        res = res + x;

        return res;
    }


    @ApiOperation(value = "修改老地名中的地理编码", notes = "修改老地名中的地理编码")
    @GetMapping(value = "/rewrite")
    public  List<GFGX_Y_DMK_DMSJ> getWorldDistrict(Integer start ,Integer end){
        List<GFGX_Y_DMK_DMSJ> gfgx_y_dmk_dmsjs = gfgx_y_dmk_dmsj_dao.rewriteDLBM(start, end);

        for (GFGX_Y_DMK_DMSJ gfgx_y_dmk_dmsj : gfgx_y_dmk_dmsjs) {
            Integer lv = gfgx_y_dmk_dmsj.getLV();

            String wz = gfgx_y_dmk_dmsj.getWZ();
            String substring = wz.substring(6, wz.length() - 1);
            String[] split = substring.split(" ");
            Double lon =Double.valueOf(split[0]);
            Double lat =Double.valueOf(split[1]);

            String newDLBM = getNewDLBM(lon,lat,lv);
            gfgx_y_dmk_dmsj.setDLBM(Long.valueOf(newDLBM));
        }
     //   gfgx_y_dmk_dmsj_dao.saveAll(gfgx_y_dmk_dmsjs);
        return  gfgx_y_dmk_dmsjs;
    }

    public String getNewDLBM (double lon,double lat ,int zoom){
        XYZ tileNumber = TileUtil.getTileNumber(lat, lon, zoom);

        String res = tileNumber.getZ().toString();
        String y = tileNumber.getY().toString();
        String x = tileNumber.getX().toString();

        for(int i = 0; i<7-y.length();i++){
            res = res+"0";
        }
        res = res + y;

        for(int i = 0; i<7-x.length();i++){
            res = res+"0";
        }
        res = res + x;

        return res;
    }
}
