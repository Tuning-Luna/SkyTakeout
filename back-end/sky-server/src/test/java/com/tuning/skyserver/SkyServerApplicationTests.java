package com.tuning.skyserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

// @SpringBootTest
class SkyServerApplicationTests {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Test
  public void test() {
    // 测试是否注入成功
    System.out.println(redisTemplate);
  }

  @Test
  public void testString() {
    String key = "test:string:key";
    String value = "Hello Redis";
    ValueOperations<String, Object> opStirng = redisTemplate.opsForValue();

    opStirng.set(key, value);
    Object result = opStirng.get(key);

    System.out.println("读取到的值: " + result);

    assert value.equals(result);
  }


  @Test
  public void testHash() {
    HashOperations<String, Object, Object> opHash = redisTemplate.opsForHash();

    opHash.put("myHash", "1", "10");
    opHash.put("myHash", "2", "20");
    opHash.put("myHash", "3", "30");
    opHash.put("myHash", "4", "40");

    System.out.println(opHash.get("myHash", "1"));

    System.out.println(opHash.keys("myHash"));

    System.out.println(opHash.values("myHash"));

  }

}
