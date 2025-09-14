package com.tuning.skyserver.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginContoller {

  @PostMapping("/login")
  public Object login() {
    return "ok";
  }
}
