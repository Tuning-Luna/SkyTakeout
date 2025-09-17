package com.tuning.controller.admin;

import com.Tuning.dto.CategoryDTO;
import com.Tuning.dto.CategoryPageQueryDTO;
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
  public ApiResult<String> addCategory(@RequestBody @Valid CategoryDTO dto) {
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
}
