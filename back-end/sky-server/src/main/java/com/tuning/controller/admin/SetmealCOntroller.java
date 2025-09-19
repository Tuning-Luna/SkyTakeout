package com.tuning.controller.admin;

import com.Tuning.dto.SetmealCreateDTO;
import com.Tuning.result.ApiResult;
import com.tuning.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/setmeal")
public class SetmealCOntroller {

  final private SetmealService setmealService;

  @Autowired
  public SetmealCOntroller(SetmealService setmealService) {
    this.setmealService = setmealService;
  }

  @PostMapping
  public ApiResult<String> insert(@RequestBody SetmealCreateDTO dto) {
    setmealService.insertWithDish(dto);
    return ApiResult.ok("添加成功");
  }
}
