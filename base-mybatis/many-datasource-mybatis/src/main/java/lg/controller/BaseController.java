package lg.controller;

import lg.config.BaseConfig;
import lg.domain.TUser;
import lg.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
