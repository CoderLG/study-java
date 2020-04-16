package lg.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lg.domain.TUser;
import lg.dvo.TUserDvo;
import lg.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping ("save")
    public Object save(@RequestBody TUserDvo user){
        TUser tUser = new TUser();
        tUser.setTName(user.getTName());
        Object res = userService.save(tUser);

        log.info("得到自增ID "+tUser.getTId());

        return tUser;
    }


    @GetMapping("findAllUser")
    public Object findAllUser(){
        List<TUser> all = userService.findAll();
        System.out.println(all.size());
        return all;
    }

    @GetMapping("findById")
    public TUser findById(@RequestParam Long id){
        return userService.findById(id);
    }


    @GetMapping("page")
    public Object page(@RequestParam int page, @RequestParam int pageSize) {
        PageHelper.startPage(page,pageSize,"t_id desc");
        List<TUser> all = userService.findAll();
        PageInfo<TUser> tUserPageInfo = new PageInfo<>(all);
        return tUserPageInfo;
    }





}
