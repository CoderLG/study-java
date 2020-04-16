package lg.controller;

import io.swagger.annotations.Api;
import lg.service.impl.CachingServiceImpl;
import lg.utils.StaticUtils;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试utils
 */
@RestController
@Api(tags = "utils测试")
public class UtilsController {

    @GetMapping("set")
    public void set(String arg) {
        StaticUtils.setProperty("aa",arg);
    }
    @GetMapping("get")
    public String get() throws Exception {
       return StaticUtils.getProperty("aa");
    }


}
