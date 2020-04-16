package lg.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lg.domain.TUser;

import java.util.Date;

/**
 * author: LG
 * date: 2019-09-09 09:39
 * desc:
 * Json web token (JWT), 是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准（(RFC 7519).
 * 该token被设计为紧凑且安全的，特别适用于分布式站点的单点登录（SSO）场景。
 * JWT的声明一般被用来在身份提供者和服务提供者间传递被认证的用户身份信息，以便于从资源服务器获取资源，
 * 也可以增加一些额外的其它业务逻辑所必须的声明信息，该token也可直接被用于认证，也可被加密。
 *
 */
public class JwtUtils {
    public static final String SUBJECT = "testToken";

    //过期时间 一个小时
    public static final long EXPIRA = 1000*60*60;

    //秘钥
    public static final String APPSECRET = "lgtest";

    public static String geneJsonWebToken(TUser tUser) {
        if (tUser == null || tUser.getTId() == null || tUser.getTName() == null) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", tUser.getTId())
                .claim("name", tUser.getTName())
                .setIssuedAt(new Date())               //发布时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRA))   //过期时间
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();       //加密 压缩

        return token;
    }

    public static Claims checkJWT(String tocken) {

        try {
            Claims claims = Jwts.parser().setSigningKey(APPSECRET)
                    .parseClaimsJws(tocken)
                    .getBody();
            return claims;
        }catch (Exception e){}
        return null;
    }

}
