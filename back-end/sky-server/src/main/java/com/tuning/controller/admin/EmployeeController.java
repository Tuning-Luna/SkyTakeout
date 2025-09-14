package com.tuning.controller.admin;

import com.Tuning.dto.EmployeeLoginDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.vo.EmployeeLoginVO;
import com.tuning.service.EmployeeService;
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

  @PostMapping("/login")
  public ApiResult<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
    System.out.println(employeeLoginDTO.getPassword().getClass());
    EmployeeLoginVO vo = employeeService.login(employeeLoginDTO);
    return ApiResult.ok(vo);
  }
}