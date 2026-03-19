package org.example.aidemo.common.Validation;

import jakarta.validation.ConstraintValidator;
import org.example.aidemo.common.anno.State;

//<给哪个注解提供规则，校验的数据类型>
public class StateValidation implements ConstraintValidator<State, String> {


    @Override
    public boolean isValid(String s, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        //参数中的 s 就是被校验的参数
        //返回的 boolean 值表示校验结果
        if (s == null)
            return false;
        if ("已发布".equals(s) || "草稿".equals(s))
            return true;
        return false;
    }
}
