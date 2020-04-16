package lg.controller;

import io.jsonwebtoken.Claims;
import lg.domain.TUser;
import lg.service.impl.UserServiceImpl;
import lg.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: LG
 * date: 2019-09-17 10:29
 * desc:
 * 模仿登录过程
 */
@RestController
public class TokenController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 输入账号密码，验证数据库，生成token/验证token，重定向到一个地址
     *
     * 扫码过程，内容复杂，多了一个更新数据库的过程
     */


    /**
     * 登录
     */
    @GetMapping("login")
    public String  login(){
        //根据账号密码查询
        TUser byId = userService.findById(22l);
        //生成token
        String token = JwtUtils.geneJsonWebToken(byId);

        return token;
    }


    /**
     * 重定向站内 并待token
     */
    @GetMapping("toLocal")
    public void  toLocal(@RequestParam String token, HttpServletResponse response) throws IOException {
        //检查token
        Claims claims = JwtUtils.checkJWT(token);
        Integer id = claims.get("id", Integer.class);
        String name = claims.get("name", String.class);

        // 重定向到本站
        response.sendRedirect("/index.html?id=i&name=r");

    }
    /**
     * 重定向站外 并待token
     */
    @GetMapping("toBaiDu")
    public void  toBaiDu(@RequestParam String token, HttpServletResponse response) throws IOException {
        //检查token
        Claims claims = JwtUtils.checkJWT(token);
        Integer id = claims.get("id", Integer.class);
        String name = claims.get("name", String.class);

        // 重定向到BaiDu
        response.sendRedirect("https://www.baidu.com?id=1&name=11");
    }

}
