package org.example.aidemo.common.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.aidemo.common.Validation.StateValidation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint( validatedBy = {StateValidation.class} )
public @interface State {

    // 校验失败后 提示的信息
    String message() default "state的参数只能是已发布或者草稿";

    //指定分组
    Class<?>[] groups() default {};

    //获取到state注解的附加信息
    Class<? extends Payload>[] payload() default {};

}
