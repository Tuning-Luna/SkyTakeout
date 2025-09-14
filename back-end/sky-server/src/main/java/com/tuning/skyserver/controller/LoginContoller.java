package com.tuning.skyserver.controller;

import com.Tuning.result.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginContoller {

  @PostMapping("/login")
  public ApiResult<String> login() {
    return ApiResult.ok("Login success");
  }
}
