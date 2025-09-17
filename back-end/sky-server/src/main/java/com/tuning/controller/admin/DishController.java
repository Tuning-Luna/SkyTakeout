package com.tuning.controller.admin;

import com.Tuning.dto.DishCreateDTO;
import com.Tuning.dto.DishPageQueryDTO;
import com.Tuning.dto.DishUpdateDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.result.PageResult;
import com.Tuning.vo.DishListVO;
import com.Tuning.vo.DishPageQueryVO;
import com.Tuning.vo.DishVO;
import com.tuning.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/page")
  public ApiResult<PageResult<DishPageQueryVO>> pageQuery(DishPageQueryDTO dto) {
    PageResult<DishPageQueryVO> pageQuery = dishService.pageQuery(dto);
    return ApiResult.ok(pageQuery);
  }

  @DeleteMapping
  public ApiResult<String> delete(@RequestParam /*加上注解后前端传递字符串可解析成List*/ List<Long> ids) {
    dishService.deleteBatch(ids);
    return ApiResult.ok();
  }


  @GetMapping("/{id}")
  public ApiResult<DishVO> getById(@PathVariable Long id) {
    DishVO vo = dishService.getWithFlavorById(id);
    return ApiResult.ok(vo);
  }

  @PutMapping
  public ApiResult<String> update(@RequestBody DishUpdateDTO dto) {
    dishService.updateWithFlavor(dto);
    return ApiResult.ok("修改成功");
  }

  @GetMapping("/list")
  public ApiResult<List<DishListVO>> getByCategoryId(Long categoryId) {
    List<DishListVO> vos = dishService.getByCategoryId(categoryId);
    return ApiResult.ok(vos);
  }

  @PostMapping("/status/{status}")
  public ApiResult<String> changeStatus(@PathVariable Integer status, Long id) {
    dishService.changeStatus(status, id);
    return ApiResult.ok("修改成功");
  }
}
