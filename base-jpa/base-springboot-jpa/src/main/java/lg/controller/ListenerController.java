package lg.controller;

import io.swagger.annotations.Api;
import lg.listener.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: LG
 * date: 2019-10-31 17:07
 * desc:
 * listener中 定义监听器 监听事件
 * 发布事件 被监听器监听到
 */
@Api(tags = "监听事件")
@RestController
public class ListenerController {

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping("testListener")
    public String testListener(){
        applicationContext.publishEvent(new MyEvent("测试事件"));
        return "ok";
    }

}
