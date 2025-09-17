package com.tuning.annotation;

import com.Tuning.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 自定义注解，用于表示需要自动填充的
@Target(ElementType.METHOD) // 只能加在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

  OperationType value(); // 指定数据库操作类型为UPDATE和INSERT
}
