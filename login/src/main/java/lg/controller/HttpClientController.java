package lg.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lg.domain.TUser;
import lg.dvo.HttpDvo;
import lg.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-06 13:35
 * desc:
 */
@RestController
@RequestMapping("/httpclient")
public class HttpClientController {



    @Autowired
    private UserServiceImpl userService;

    @GetMapping("getAndNoResult")
    public void findById(@RequestParam Long id){
        System.out.println("id :" + id);
    }

    @PostMapping ("saveHttpDvo")
    public Object saveHttpDvo(@RequestBody HttpDvo httpDvo){
        String s = httpDvo.toString();
        System.out.println(s);
        return s;
    }

    @PostMapping ("saveHttpDvNoBody")
    public Object saveHttpDvNoBody( HttpDvo httpDvo){

        String s = httpDvo.toString();
        System.out.println(s);
        return s;

    }

    @PostMapping ("postSaveString")
    public Object postSaveString(@RequestParam  String str){
        System.out.println(str);
        return str;
    }


}
