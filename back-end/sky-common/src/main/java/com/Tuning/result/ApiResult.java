package com.Tuning.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> {
  private Integer code;   // HTTP 状态码
  private String message; // 提示信息
  private T data;         // 业务数据

  private static final Integer SUCCESS = 0;

  public static <T> ApiResult<T> ok(T data) {
    return new ApiResult<>(SUCCESS, "ok", data);
  }

  public static <T> ApiResult<T> ok() {
    return new ApiResult<>(SUCCESS, "ok", null);
  }

  public static <T> ApiResult<T> ok(T data, String message) {
    return new ApiResult<>(SUCCESS, message, data);
  }

  public static <T> ApiResult<T> fail(HttpStatus status, String message) {
    return new ApiResult<>(status.value(), message, null);
  }
}
