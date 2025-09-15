package com.tuning.controller.admin;

import com.Tuning.dto.EmployeeCreateDTO;
import com.Tuning.dto.EmployeeLoginDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.vo.EmployeeLoginVO;
import com.tuning.service.EmployeeService;
import jakarta.validation.Valid;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {

  final private EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // 登录
  @PostMapping("/login")
  public ApiResult<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
    EmployeeLoginVO vo = employeeService.login(employeeLoginDTO);
    return ApiResult.ok(vo);
  }

  // 新增
  @PostMapping
  public ApiResult<Null> insert(@RequestBody @Valid EmployeeCreateDTO employeeCreateDTO) {
    employeeService.insert(employeeCreateDTO);
    return ApiResult.ok(null, "添加员工成功");
  }

}