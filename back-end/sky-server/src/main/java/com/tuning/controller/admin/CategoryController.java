package com.tuning.controller.admin;

import com.Tuning.dto.CategoryCreateDTO;
import com.Tuning.dto.CategoryPageQueryDTO;
import com.Tuning.dto.CategoryUpdateDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.result.PageResult;
import com.Tuning.vo.CategoryQueryVO;
import com.tuning.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

  final private CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  public ApiResult<String> addCategory(@RequestBody @Valid CategoryCreateDTO dto) {
    categoryService.addCategory(dto);
    return ApiResult.ok("添加成功");
  }

  @GetMapping("/page")
  public ApiResult<PageResult<CategoryQueryVO>>
  page(@Valid CategoryPageQueryDTO dto) {
    System.out.println(dto);
    PageResult<CategoryQueryVO> res = categoryService.page(dto);
    return ApiResult.ok(res);
  }

  @DeleteMapping
  public ApiResult<String> deleteById(Long id) {
    categoryService.deleteById(id);
    return ApiResult.ok("成功删除");
  }

  @GetMapping("/{id}")
  public ApiResult<CategoryQueryVO> getById(@PathVariable Long id) {
    CategoryQueryVO vo = categoryService.selectById(id);
    return ApiResult.ok(vo);
  }

  @PutMapping
  public ApiResult<String> update(@RequestBody @Valid CategoryUpdateDTO dto) {
    categoryService.update(dto);
    return ApiResult.ok();
  }

  @PutMapping("/status/{status}")
  public ApiResult<String> startOrStop(@PathVariable("status") Integer status, Long id) {
    categoryService.startOrStop(status, id);
    return ApiResult.ok();
  }

}
