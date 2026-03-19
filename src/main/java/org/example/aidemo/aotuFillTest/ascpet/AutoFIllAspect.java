package org.example.aidemo.aotuFillTest.ascpet;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.aidemo.aotuFillTest.AutoFill;
import org.example.aidemo.aotuFillTest.OperationType;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class AutoFIllAspect {
    /*
            * org.example.aidemo.aotuFillTest.mapper.*.*(..)
              *   --> 返回值类型：任意
              org.example.aidemo.aotuFillTest.mapper.*  --> mapper 包下的任意类
              .*    --> 类中的任意方法
              (..)  -->  任意参数（0 个或多个）
     */
    @Pointcut("execution(* org.example.aidemo.aotuFillTest.mapper.*.*(..) ) && @annotation(org.example.aidemo.aotuFillTest.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("Aop started");
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        Object entiy = args[0];
        LocalDateTime now = LocalDateTime.now();
        //todo
//        Map<String, Object> claims=ThreadLocalUtil.get();
//        Integer userId = (Integer) claims.get("id");

        if (operationType == OperationType.INSERT){

            try {
                Method setCreateTime = entiy.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setUpdateTime = entiy.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                setCreateTime.invoke(entiy, now);
                setUpdateTime.invoke(entiy, now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else if (operationType == OperationType.UPDATE){
            try {
                Method setUpdateTime = entiy.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                setUpdateTime.invoke(entiy, now);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Around("autoFillPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        //ProceedingJoinPoint是 JoinPoint 的子接口，只能用于 @Around
        /*
        joinPoint.getArgs();        // 方法参数
        joinPoint.getSignature();  // 方法签名
        joinPoint.getTarget();     // 目标对象
        joinPoint.proceed();       // 执行目标方法
         */

        // 1️⃣ 方法执行前
        System.out.println("AOP around------before");

        // 2️⃣ 执行目标方法（非常关键）
        Object result = joinPoint.proceed();

        // 3️⃣ 方法执行后
        System.out.println("AOP around------after");

        // 4️⃣ 返回结果

    }
}
