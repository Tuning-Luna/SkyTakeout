package com.tuning.aspect;


import com.Tuning.OperationType;
import com.Tuning.context.BaseContext;
import com.tuning.annotation.AutoFill;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {

  // 切入点
  // 切入所有类，所有方法。然后再筛选有AutoFill注解的
  @Pointcut("execution(* com.tuning.mapper.*.*(..)) && @annotation(com.tuning.annotation.AutoFill)")
  public void autoFillPointCut() {
  }

  // 前置通知，为公共字段赋值
  @Before("autoFillPointCut()")
  public void autoFill(JoinPoint jp) {
    // 1. 获取方法签名
    MethodSignature signature = (MethodSignature) jp.getSignature();
    Method method = signature.getMethod();

    // 2. 获取方法上的注解
    AutoFill autoFill = method.getAnnotation(AutoFill.class);
    OperationType opType = autoFill.value();

    // 3. 获取方法参数（这里假设第一个参数是实体，做好了约定才能进行AOP）
    Object[] args = jp.getArgs();
    if (args == null || args.length == 0) {
      return; // 防止为空
    }
    Object entity = args[0];


    // 4. 公共字段
    LocalDateTime now = LocalDateTime.now();
    Long userId = BaseContext.getCurrentId(); // 当前登录用户id

    // 5. 根据操作类型填充
    if (opType == OperationType.INSERT) {
      setFieldValue(entity, "createTime", now);
      setFieldValue(entity, "updateTime", now);
      setFieldValue(entity, "createUser", userId);
      setFieldValue(entity, "updateUser", userId);
    }
    else if (opType == OperationType.UPDATE) {
      setFieldValue(entity, "updateTime", now);
      setFieldValue(entity, "updateUser", userId);
    }
  }


  // 通过反射给对象属性赋值
  private void setFieldValue(Object target, String fieldName, Object value) {
    try {
      Field field = target.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(target, value);
    } catch (NoSuchFieldException e) {
      // 没有这个字段就忽略，不报错
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
