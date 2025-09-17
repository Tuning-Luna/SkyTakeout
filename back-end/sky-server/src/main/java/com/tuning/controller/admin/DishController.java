package com.tuning.controller.admin;

import com.Tuning.dto.DishCreateDTO;
import com.Tuning.result.ApiResult;
import com.tuning.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
  final private DishService dishService;

  @Autowired
  public DishController(DishService dishService) {
    this.dishService = dishService;
  }

  @PostMapping
  public ApiResult<String> insert(@RequestBody DishCreateDTO dto) {
    dishService.insertWithFlavor(dto);
    return ApiResult.ok("新增菜品成功");
  }
}
