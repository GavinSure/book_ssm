package com.Gavin.interceptor;

import com.Gavin.common.StatusEnum;
import com.Gavin.exception.MyRuntimeException;
import com.Gavin.util.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @author: Gavin
 * @description:
 * @className: LoginInterceptor
 * @date: 2022/3/7 10:58
 * @version:0.1
 * @since: jdk14.0
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        //放行复杂请求方式（跨域请求也是拦截器）
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }


     //验证token的合法性
        String token=request.getHeader("Authorization");
     if (!StringUtils.hasLength(token)){
         //证明token为null
        throw new MyRuntimeException(StatusEnum.NO_LOGIN);
     }
     String[] array=token.split(" ");
     if (array.length==0)
         throw new MyRuntimeException(StatusEnum.NO_LOGIN);
     //验证token
        try {
            tokenService.verifyToken(array[1]);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyRuntimeException(StatusEnum.NO_LOGIN);
        }
        return true;
    }
}
