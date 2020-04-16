package lg.controller.admin;

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
@RequestMapping("/admin")
public class UserAdminController {


    @Autowired
    private UserServiceImpl userService;



    @PutMapping("update")
    public int update(@RequestParam Long id,@RequestParam String name){
        TUser tUser = new TUser();
        tUser.setTId(id);
        tUser.setTName(name);
        return userService.update(tUser);

    }

    @DeleteMapping("delete")
    public int delete( @RequestParam Long id){
       return userService.delete(id);
    }

}
