package com.tuning.controller.user;

import com.Tuning.dto.ShoppingCartDTO;
import com.Tuning.entity.ShoppingCart;
import com.Tuning.result.ApiResult;
import com.tuning.service.impl.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/list")
  public ApiResult<List<ShoppingCart>> list() {
    return ApiResult.ok(shoppingCartService.showShoppingCart());
  }
}
