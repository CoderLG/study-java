package lg.controller;

import io.swagger.annotations.Api;
import lg.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "自带方法")
@RestController
public class UserController {

    @Autowired
    private ServiceImpl service;

    @GetMapping("test")
    public void test(){
        service.saveDouble();
    }

    @GetMapping("test2")
    public void test2(){
        service.saveDoubleFail2();
    }

    @GetMapping("test3")
    public void test3(){
        service.saveDoubleSuss();
    }


}
