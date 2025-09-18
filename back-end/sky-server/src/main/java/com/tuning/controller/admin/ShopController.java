package com.tuning.controller.admin;

import com.Tuning.result.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {

  final RedisTemplate<String, Object> redisTemplate;

  final static private String SHOP_STATUS_KEY = "SHOP_STATUS";

  @Autowired
  public ShopController(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PutMapping("/{status}")
  public ApiResult<String> setStatus(@PathVariable Integer status) {
    redisTemplate.opsForValue().set(SHOP_STATUS_KEY, status);

    return ApiResult.ok();
  }

  @GetMapping("/status")
  public ApiResult<Integer> getStatus() {
    Integer shopStatus = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS_KEY);

    return ApiResult.ok(shopStatus);
  }
}
