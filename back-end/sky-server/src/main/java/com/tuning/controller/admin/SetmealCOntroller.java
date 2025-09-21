package com.tuning.controller.admin;

import com.Tuning.dto.SetmealCreateDTO;
import com.Tuning.dto.SetmealPageQueryDTO;
import com.Tuning.dto.SetmealUpdateDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.result.PageResult;
import com.Tuning.vo.SetmealVO;
import com.tuning.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
public class SetmealCOntroller {

  final private SetmealService setmealService;

  @Autowired
  public SetmealCOntroller(SetmealService setmealService) {
    this.setmealService = setmealService;
  }

  @PostMapping
  @CacheEvict(cacheNames = "setmealCache", key = "#dto.categoryId")
  public ApiResult<String> insert(@RequestBody SetmealCreateDTO dto) {
    setmealService.insertWithDish(dto);
    return ApiResult.ok("添加成功");
  }

  @GetMapping("/page")
  public ApiResult<PageResult<SetmealVO>>
  pageQuery(SetmealPageQueryDTO dto) {
    PageResult<SetmealVO> pageResult = setmealService.page(dto);
    return ApiResult.ok(pageResult);
  }

  @DeleteMapping
  @CacheEvict(cacheNames = "setmealCache", allEntries = true)
  public ApiResult<String> delete(@RequestParam List<Integer> ids) {
    setmealService.deleteBatch(ids);
    return ApiResult.ok();
  }

  @GetMapping("/{id}")
  public ApiResult<SetmealVO> getById(@PathVariable Long id) {
    SetmealVO setmealVO = setmealService.getByIdWithDish(id);
    return ApiResult.ok(setmealVO);
  }

  @PutMapping
  @CacheEvict(cacheNames = "setmealCache", allEntries = true)
  public ApiResult<String> update(@RequestBody SetmealUpdateDTO dto) {
    setmealService.update(dto);
    return ApiResult.ok();
  }

  @PostMapping("status/{status}")
  @CacheEvict(cacheNames = "setmealCache", allEntries = true)
  public ApiResult<String>
  changeStatus(@PathVariable Integer status, Long id) {
    setmealService.changeStatus(status, id);
    return ApiResult.ok();
  }

}
