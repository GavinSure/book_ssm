package com.Gavin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

/**
 * @author: Gavin
 * @description:
 * @className: TokenService
 * @date: 2022/3/11 14:41
 * @version:0.1
 * @since: jdk14.0
 */

@Component
public class TokenService {

    private static final String SECRET="@#$@@!GAVIN";

    //生成token
    public String createToken(long adminId){
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withIssuer("Gavin")              //jwt签发者
                .withSubject("登陆成功token")       //jwt所面向用户
                .withExpiresAt(new Date(System.currentTimeMillis()+ Duration.ofMinutes(10).toMillis())) //jwt过期时间
                .withClaim("adminId",adminId)
                .sign(algorithm);
    }
    //验证token
    public DecodedJWT verifyToken(String token){
        Algorithm algorithm=Algorithm.HMAC256(SECRET);
        JWTVerifier verifier=JWT.require(algorithm)
                .withIssuer("Gavin")
                .build();
        return verifier.verify(token);
    }

}
