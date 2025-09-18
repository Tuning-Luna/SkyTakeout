package com.tuning.controller.user;

import com.Tuning.result.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopContoller") // 由于有两个类名相同，所以需要避免冲突
@RequestMapping("/user/shop")
public class ShopController {

  final RedisTemplate<String, Object> redisTemplate;

  final static private String SHOP_STATUS_KEY = "SHOP_STATUS";

  @Autowired
  public ShopController(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @GetMapping("/status")
  public ApiResult<Integer> getStatus() {
    Integer shopStatus = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS_KEY);

    return ApiResult.ok(shopStatus);
  }
}
