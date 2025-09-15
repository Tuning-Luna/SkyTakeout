package com.Tuning.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

  // 默认签名密钥，可通过配置文件或环境变量注入
  private static final String SECRET_KEY_STR = "Tuning-Sercet-Tuning-Sercet-123456";
  // ⚠️ HS256 要求 key 长度 >= 32 字节

  private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STR.getBytes(StandardCharsets.UTF_8));

  // 默认过期时间：31day
  private static final long DEFAULT_EXPIRE_MILLIS = 60L * 60 * 1000 * 744;

  /**
   * 生成 token（默认过期时间）
   */
  public static String generateToken(Map<String, Object> claims) {
    return generateToken(claims, DEFAULT_EXPIRE_MILLIS);
  }

  /**
   * 生成 token（自定义过期时间）
   */
  public static String generateToken(Map<String, Object> claims, long expireMillis) {
    Instant now = Instant.now();
    Instant exp = now.plusMillis(expireMillis);

    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(exp))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * 解析 token 并返回 payload Map
   */
  public static Map<String, Object> parseToken(String token) throws JwtException {
    Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();

    return new HashMap<>(claims);
  }

  /**
   * 校验 token 是否有效（签名和过期时间）
   */
  public static boolean validateToken(String token) {
    try {
      parseToken(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }
}
