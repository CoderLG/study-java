package lg.controller;


import io.swagger.annotations.Api;
import lg.service.impl.AsyncServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: LG
 * date: 2019-08-18 14:25
 * desc:
 * 异步调用
 *
 * 当返回值为异步计算的结果时候
 * 主线程并不会等待
 */
@Slf4j
@Controller
@Api(tags = "异步调用")
public class AsyncController {

    @Autowired
    private AsyncServiceImpl asyncService;

    @GetMapping("/getAsync")
    @ResponseBody
    public String getAsync(){

        System.out.println("1");
        asyncService.getAsync();
        System.out.println("4");
        return "suss";
    }

    @GetMapping("/getAsync2")
    @ResponseBody
    public String getAsync2(){

        System.out.println("1");
        String async = asyncService.getAsync();
        System.out.println("4");
        return async;
    }

}
