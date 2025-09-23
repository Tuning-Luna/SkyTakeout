package com.tuning.controller.user;

import com.Tuning.dto.ShoppingCartDTO;
import com.Tuning.result.ApiResult;
import com.tuning.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {
  final private ShoppingCartServiceImpl shoppingCartService;

  @Autowired
  public ShoppingCartController(ShoppingCartServiceImpl shoppingCartService) {
    this.shoppingCartService = shoppingCartService;
  }

  @PostMapping("/add")
  public ApiResult<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
    shoppingCartService.addShoppingCart(shoppingCartDTO);
    return ApiResult.ok();
  }
}
