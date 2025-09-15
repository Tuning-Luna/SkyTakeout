package com.tuning.service.impl;

import com.Tuning.dto.EmployeeCreateDTO;
import com.Tuning.dto.EmployeeLoginDTO;
import com.Tuning.entity.Employee;
import com.Tuning.exception.BizException;
import com.Tuning.utils.JWTUtil;
import com.Tuning.utils.PasswordUtil;
import com.Tuning.vo.EmployeeLoginVO;
import com.tuning.mapper.EmployeeMapper;
import com.tuning.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    if (! PasswordUtil.matches(employeeLoginDTO.getPassword(), employee.getPassword())) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "密码错误");
    }
    if (employee.getStatus() == 0) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "此员工已被禁用");
    }

    // 组装VO对象返回给前端
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

  @Override
  public void insert(EmployeeCreateDTO employeeCreateDTO) {
    //   TODO:此处应有表单校验

    boolean exists = employeeMapper.existsByUsername(employeeCreateDTO.getUsername());

    if (exists) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "用户已存在");
    }

    Employee employee = new Employee();

    // 从DTO拷贝部分数据到Entity
    BeanUtils.copyProperties(employeeCreateDTO, employee);

    employee.setId(null);  // 重置ID，让数据库递增自己生成
    employee.setCreateTime(LocalDateTime.now());
    employee.setUpdateTime(LocalDateTime.now());
    employee.setPassword(PasswordUtil.encrypt("123456")); // 密码默认123456
    employee.setStatus(1); // 默认是1，启用权限

    //   TODO:还有create_user和update_user需要填写

    Integer rows = employeeMapper.insertEmployee(employee);
    if (rows != 1) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "插入失败");
    }
    System.out.println("插入成功，新的id：" + employee.getId());
  }
}
