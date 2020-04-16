package lg.utils;

import io.jsonwebtoken.Claims;
import lg.domain.TUser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-09-17 10:16
 * desc:
 */
public class JwtUtilsTest {

    @Test
    public void geneJsonWebToken() {
        TUser tUser = new TUser();
        tUser.setTId(1l);
        tUser.setTName("gg");

        String s = JwtUtils.geneJsonWebToken(tUser);
        System.out.println("tocker:" + s);


    }

    @Test
    public void checkJWT() {
        String str = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VG9rZW4iLCJpZCI6MSwibmFtZSI6ImdnIiwiaWF0IjoxNTY4Njg2Nzc4LCJleHAiOjE1Njg2OTAzNzh9.1tw3-ClZl5G-U7QM44MCzD-c3uwtIb6gEllNb7zUQdw";
        Claims claims = JwtUtils.checkJWT(str);
        System.out.println("check:" + claims);
    }
}