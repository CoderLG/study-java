package lg.controller.admin;

import io.swagger.annotations.Api;
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
@RequestMapping("/admin/user")
public class UserAdminController {

    @Autowired
    private UserServiceImpl userService;


    @PutMapping("update")
    public int update(@RequestBody TUser tUser){
        return userService.update(tUser);
    }

    @PutMapping("delete")
    public int delete( @RequestParam Long id){
       return userService.delete(id);
    }

    @PutMapping("deleteAll")
    public int deleteAll(){
        return userService.deleteAll();
    }



}
