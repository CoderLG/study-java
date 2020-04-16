package lg.controller;

import io.swagger.annotations.Api;
import lg.dvo.CarVo;
import lg.feign.GeoserverFeign;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: LG
 * date: 2020-03-16 10:15
 * desc:
 */
@Api(tags = "feign")
@RestController
@RequestMapping("/feign")
public class FeignController {
    @Autowired
    GeoserverFeign geoserverFeign;

    @ResponseBody
    @GetMapping("getMapping1")
    public String getMapping1(){
        String s = geoserverFeign.publishLayer1();
        return s;
    }

    @ResponseBody
    @GetMapping("getMapping2")
    public String getMapping2(){
        String s = geoserverFeign.publishLayer2("workSpaceName","Basic YWRtaW46Z2Vvc2VydmVy");
        return s;
    }

    @ResponseBody
    @GetMapping("getMapping3")
    public CarVo getMapping3(){
        CarVo carVo = new CarVo();
        carVo.setAge(11);
        carVo.setName("mm");
        CarVo s = geoserverFeign.publishLayer3("workSpaceName",carVo,"Basic YWRtaW46Z2Vvc2VydmVy");
        return s;
    }

}
