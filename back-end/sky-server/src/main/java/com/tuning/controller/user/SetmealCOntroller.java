package com.tuning.controller.user;

import com.Tuning.entity.Setmeal;
import com.Tuning.result.ApiResult;
import com.Tuning.vo.DishItemVO;
import com.tuning.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
public class SetmealCOntroller {

  final private SetmealService setmealService;

  @Autowired
  public SetmealCOntroller(SetmealService setmealService) {
    this.setmealService = setmealService;
  }

  @GetMapping("/list")
  // 开启缓存
  @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
  public ApiResult<List<Setmeal>> list(Long categoryId) {
    Setmeal setmeal = new Setmeal();
    setmeal.setCategoryId(categoryId);
    setmeal.setStatus(1);

    List<Setmeal> list = setmealService.list(setmeal);
    return ApiResult.ok(list);
  }

  @GetMapping("/dish/{id}")
  public ApiResult<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
    List<DishItemVO> list = setmealService.getDishItemById(id);
    return ApiResult.ok(list);
  }
}
