package com.tuning.controller.admin;

import com.Tuning.context.BaseContext;
import com.Tuning.dto.EmployeeCreateDTO;
import com.Tuning.dto.EmployeeLoginDTO;
import com.Tuning.dto.EmployeePageQueryDTO;
import com.Tuning.dto.EmployeeUpdateDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.result.PageResult;
import com.Tuning.vo.EmployeeLoginVO;
import com.Tuning.vo.EmployeeQueryVO;
import com.tuning.service.EmployeeService;
import jakarta.validation.Valid;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

  // 登出
  @PostMapping("/logout")
  public ApiResult<String> logout() {
    return ApiResult.ok("id为" + BaseContext.getCurrentId() + "的职工成功退出登录");
  }

  // 分页查询+name筛选
  @GetMapping("/page")
  public ApiResult<PageResult<EmployeeQueryVO>>
  page(@Valid EmployeePageQueryDTO queryDTO) {
    PageResult<EmployeeQueryVO> result = employeeService.page(queryDTO);
    return ApiResult.ok(result, "查询成功");
  }

  // 改变状态（禁用 / 启用）
  @PostMapping("/status/{status}")
  public ApiResult<String> updateStatus(
          @PathVariable Integer status,   // 路径参数
          @RequestParam String id) {      // 查询参数

    // 调用 Service 层处理逻辑
    employeeService.updateStatusById(id, status);

    return ApiResult.ok("状态更新成功");
  }

  // 根据id查询员工
  @GetMapping("/{id}")
  public ApiResult<EmployeeQueryVO> get(@PathVariable Long id) {
    return ApiResult.ok(employeeService.getById(id));
  }

  // 更新用户信息
  @PutMapping
  public ApiResult<String> update(@RequestBody @Valid EmployeeUpdateDTO employeeDTO) {
    employeeService.updateById(employeeDTO);
    return ApiResult.ok("编辑成功");
  }

}