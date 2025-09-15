package com.Tuning.exception;

import com.Tuning.result.ApiResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 业务异常
  @ExceptionHandler(BizException.class)
  public ResponseEntity<ApiResult<Void>> handleBizException(BizException ex) {
    return new ResponseEntity<>(
            ApiResult.fail(ex.getStatus(), ex.getMessage()),
            ex.getStatus()
    );
  }

  // 表单校验异常
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResult<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    // 取第一个错误消息
    String errorMessage = ex.getBindingResult()
            .getAllErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .findFirst()
            .orElse("参数不合法");

    return new ResponseEntity<>(
            ApiResult.fail(HttpStatus.BAD_REQUEST, errorMessage),
            HttpStatus.BAD_REQUEST
    );
  }

  // 500 服务器内部错误（兜底）
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResult<Void>> handleException(Exception ex) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return new ResponseEntity<>(ApiResult.fail(status, "服务器内部错误"), status);
  }
}