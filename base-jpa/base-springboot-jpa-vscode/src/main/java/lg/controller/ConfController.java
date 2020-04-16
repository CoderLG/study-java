package lg.controller;

import io.swagger.annotations.Api;
import lg.config.BaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: LG
 * date: 2020-02-25 17:04
 * desc:
 *
 * 可以在属性上
 * 可以在方法上
 *      如果方法上有较多的变量，会重复
 *
 * 可以则bean上  ，长用此方法
 */
@Api(tags = "配置文件")
@RestController
public class ConfController {
    @Value("${name}")
    private String name;
    @Value("${url}")
    private String url;

    // 长用此方法
    @Autowired
    BaseConfig baseConfig;

    @GetMapping("/getAppName")
    public String getAppName(){
        return name;
    }

    @GetMapping("/getUrl")
    public String getUrl(){
        return url;
    }

    @Value("${Dbname}")
    public void setUsersss(String name,String otherName) {
        String ab = otherName+"-->other";
        System.out.println("name-->" + name);
        System.out.println("otherName-->" + ab);
    }


    @GetMapping("/getBaseConfig")
    public String getBaseConfig(){
        return baseConfig.getName();
    }


}
