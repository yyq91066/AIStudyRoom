package org.example.aidemo.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Redis操作日志切面
 * 用于在接口调用时记录Redis操作的详细日志
 */
@Aspect
@Component
@Slf4j
public class RedisLogAspect {

    /**
     * 定义切点：拦截所有RedisTemplate和StringRedisTemplate的方法调用
     */
    @Around("execution(* org.springframework.data.redis.core.RedisTemplate.*(..)) || " +
            "execution(* org.springframework.data.redis.core.StringRedisTemplate.*(..))")
    public Object logRedisOperations(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = signature.getDeclaringTypeName();
        
        // 过滤掉一些不需要记录的内部方法
        if (shouldSkipLogging(methodName)) {
            return joinPoint.proceed();
        }
        
        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        
        // 记录Redis操作开始日志
        long startTime = System.currentTimeMillis();
        log.info("=== Redis操作开始 ===");
        log.info("类名: {}", className);
        log.info("方法: {}", methodName);
        log.info("参数: {}", formatArgs(args));
        
        try {
            // 执行目标方法
            Object result = joinPoint.proceed();
            
            // 计算执行时间
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            // 记录Redis操作成功日志
            log.info("结果: {}", result != null ? result.toString() : "null");
            log.info("耗时: {} ms", duration);
            log.info("=== Redis操作结束 ===");
            
            return result;
        } catch (Throwable throwable) {
            // 记录Redis操作异常日志
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            log.error("=== Redis操作异常 ===");
            log.error("类名: {}", className);
            log.error("方法: {}", methodName);
            log.error("参数: {}", formatArgs(args));
            log.error("耗时: {} ms", duration);
            log.error("异常信息: ", throwable);
            log.error("=== Redis操作异常结束 ===");
            
            throw throwable;
        }
    }
    
    /**
     * 判断是否应该跳过日志记录
     */
    private boolean shouldSkipLogging(String methodName) {
        // 跳过getter/setter方法
        if (methodName.startsWith("get") && !methodName.startsWith("getOps") || 
            methodName.startsWith("set") || 
            methodName.startsWith("is") || 
            methodName.startsWith("has")) {
            return true;
        }
        
        // 跳过Object类的基本方法
        if (methodName.equals("toString") || 
            methodName.equals("hashCode") ||
            methodName.equals("equals") || 
            methodName.equals("getClass")) {
            return true;
        }
        
        // 跳过生命周期方法
        if (methodName.equals("afterPropertiesSet") ||
            methodName.equals("destroy")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 格式化参数输出，避免过长的参数影响日志可读性
     */
    private String formatArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            
            Object arg = args[i];
            if (arg == null) {
                sb.append("null");
            } else if (arg instanceof String) {
                String str = (String) arg;
                if (str.length() > 100) {
                    sb.append(str.substring(0, 100)).append("...");
                } else {
                    sb.append(str);
                }
            } else {
                sb.append(arg.toString());
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
