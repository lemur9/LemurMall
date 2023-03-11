package org.lemur.common.vaild;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private final Set<Integer> set = new HashSet<>();

    /**
     * 初始化方法
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        Arrays.stream(vals).forEach(v -> set.add(v));
    }

    /**
     * 判断是否校验成功
     *
     * @param integer                    需要校验的值
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(integer);
    }
}
