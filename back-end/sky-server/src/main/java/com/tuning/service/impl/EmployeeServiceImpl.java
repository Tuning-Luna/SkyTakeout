package com.tuning.service.impl;

import com.Tuning.dto.EmployeeLoginDTO;
import com.Tuning.entity.Employee;
import com.Tuning.exception.BizException;
import com.Tuning.utils.JWTUtil;
import com.Tuning.utils.PasswordUtil;
import com.Tuning.vo.EmployeeLoginVO;
import com.tuning.mapper.EmployeeMapper;
import com.tuning.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  final private EmployeeMapper employeeMapper;

  @Autowired
  public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
    this.employeeMapper = employeeMapper;
  }

  @Override
  public EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO) {
    Employee employee = employeeMapper.selectByUsername(employeeLoginDTO.getUsername());

    if (employee == null) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "用户不存在");
    }
    System.out.println(PasswordUtil.encrypt("123456"));
    if (! PasswordUtil.matches(employeeLoginDTO.getPassword(), employee.getPassword())) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "密码错误");
    }


    Map<String, Object> claims = new HashMap<>();
    claims.put("id", employee.getId());
    claims.put("username", employee.getUsername());
    claims.put("name", employee.getName());
    String token = JWTUtil.generateToken(claims);

    return EmployeeLoginVO.builder()
            .id(Math.toIntExact(employee.getId()))
            .name(employee.getName())
            .userName(employee.getUsername())
            .token(token)
            .build();
  }
}
