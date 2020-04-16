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
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PageHelper pageHelper;


    @PostMapping ("save")
    public Object save(@RequestBody TUser tUser){
        Object res = userService.save(tUser);
        System.out.println("tid = "+tUser.getTId());
        return tUser;

    }


    @GetMapping("findAllUser")
    public Object findAllUser(){
        List<TUser> all = userService.findAll();
        System.out.println(all.size());
        return all;
    }

    @GetMapping("page")
    public Object page(@RequestParam int page, @RequestParam int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<TUser> all = userService.findAll();
        PageInfo<TUser> tUserPageInfo = new PageInfo<>(all);
        return tUserPageInfo;
    }


    @GetMapping("findById")
    public TUser findById(@RequestParam Long id){
            return userService.findById(id);
    }



}
