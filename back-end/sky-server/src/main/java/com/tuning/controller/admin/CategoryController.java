package com.tuning.controller.admin;

import com.Tuning.dto.CategoryDTO;
import com.Tuning.result.ApiResult;
import com.tuning.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
