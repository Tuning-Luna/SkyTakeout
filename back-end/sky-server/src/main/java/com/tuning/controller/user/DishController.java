package com.tuning.controller.user;

import com.Tuning.entity.Dish;
import com.Tuning.result.ApiResult;
import com.Tuning.vo.DishVO;
import com.tuning.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {
  final private DishService dishService;

  @Autowired
  public DishController(DishService dishService) {
    this.dishService = dishService;
  }

  @GetMapping("/list")
  public ApiResult<List<DishVO>> list(Long categoryId) {
    Dish dish = new Dish();
    dish.setCategoryId(categoryId);
    dish.setStatus(1); // 只查询正在起售的菜品
    List<DishVO> list = dishService.listWishFlavor(dish);
    return ApiResult.ok(list);
  }
}
