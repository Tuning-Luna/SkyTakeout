package com.tuning.service;

import com.Tuning.dto.*;
import com.Tuning.result.PageResult;
import com.Tuning.vo.EmployeeLoginVO;
import com.Tuning.vo.EmployeeQueryVO;
import jakarta.validation.Valid;

public interface EmployeeService {
  EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

  void insert(EmployeeCreateDTO employeeCreateDTO);

  PageResult<EmployeeQueryVO> page(EmployeePageQueryDTO queryDTO);

  void updateStatusById(String id, Integer status);

  void updateById(@Valid EmployeeUpdateDTO employeeDTO);

  EmployeeQueryVO getById(Long id);

  void upddatePassword(@Valid EmployeePasswordUpdateDTO employeePasswordUpdateDTO);
}
