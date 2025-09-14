package com.Tuning.utils;

// java: 程序包org.springframework.security.crypto.bcrypt不存在

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  // 加密存储
  public static String encrypt(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  // 验证密码
  public static boolean matches(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}