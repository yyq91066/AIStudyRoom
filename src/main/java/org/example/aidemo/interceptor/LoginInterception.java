package org.example.aidemo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.aidemo.common.util.JwtUtil;
import org.example.aidemo.common.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterception implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        //令牌验证
        String authorization = request.getHeader("Authorization");
        try {


            //从redis获取token
            ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
            String redisToken = stringStringValueOperations.get(authorization);
            if (redisToken == null) {
                throw new RuntimeException("token已过期");
            }

            Map<String, Object> claims = JwtUtil.parseToken(authorization);
            ThreadLocalUtil.set(claims); //使用ThreadLocal保存数据，实现共享变量
            return true;//代表放行
        }catch (Exception e){
            response.setStatus(401);
            //不放行
            return false;

        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        ThreadLocalUtil.remove();//在请求完成后 清空Threadlocal中的数据 防止内存泄漏

    }
}
