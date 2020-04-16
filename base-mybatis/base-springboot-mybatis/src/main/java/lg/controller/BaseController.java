package lg.controller;

import io.swagger.annotations.ApiParam;
import lg.config.BaseConfig;
import lg.domain.TUser;
import lg.service.impl.UserServiceImpl;
import lg.utils.RequestUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * author: LG
 * date: 2019-09-06 13:35
 * desc:
 */
@RestController
@RequestMapping("/base")
public class BaseController {

    @Autowired
    private BaseConfig baseConfig;

    @GetMapping("baseConf")
    public String baseConf(){
        return baseConfig.getVersion() ;
    }

    @GetMapping(value = "/testRequestUrl/{dataLayerName}/**")
    public void testRequestUrl(@PathVariable String dataLayerName, HttpServletRequest request) throws IOException {
        String relativePath = RequestUrlUtils.extractPathFromPattern(request);
    }

}
