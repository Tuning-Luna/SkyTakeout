package com.tuning.service;

import com.Tuning.dto.EmployeeCreateDTO;
import com.Tuning.dto.EmployeeLoginDTO;
import com.Tuning.dto.EmployeePageQueryDTO;
import com.Tuning.result.PageResult;
import com.Tuning.vo.EmployeeLoginVO;
import com.Tuning.vo.EmployeePageQueryVO;

public interface EmployeeService {
  EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

  void insert(EmployeeCreateDTO employeeCreateDTO);

  PageResult<EmployeePageQueryVO> page(EmployeePageQueryDTO queryDTO);

  void updateStatusById(String id, Integer status);
}
