package com.tuning.service;

import com.Tuning.dto.EmployeeLoginDTO;
import com.Tuning.vo.EmployeeLoginVO;

public interface EmployeeService {
  EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);
}
