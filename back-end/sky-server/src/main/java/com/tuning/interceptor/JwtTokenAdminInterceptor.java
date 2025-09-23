package com.tuning.interceptor;

import com.Tuning.context.BaseContext;
import com.Tuning.exception.BizException;
import com.Tuning.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler) {

    // 假设携带的头文件就是token
    String token = request.getHeader("token");

    if (token == null || token.isEmpty()) {
      throw new BizException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "没有token");
    }

    if (! JWTUtil.validateToken(token)) {
      throw new BizException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "token不合法");
    }


    Map<String, Object> emp = JWTUtil.parseToken(token);
    Long empId = Long.valueOf(emp.get("id").toString());
    BaseContext.setCurrentId(empId);

    return true;
  }
}
