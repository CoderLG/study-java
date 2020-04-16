package geovis.services.controller;

import geovis.services.dto.XYZ;
import geovis.services.utils.TileUtil;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-11-26 09:13
 * desc:
 */
public class DistrictControllerTest {

    @Test
    public void test1() {
//        POINT(117.930371 33.745331)
        String s = "1300056310013559";
        XYZ tileNumber = TileUtil.getTileNumber(33.745331, 117.930371, 13);

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

        System.out.println(res);
//       1300056310013559
//       1300056310013559
    }
}