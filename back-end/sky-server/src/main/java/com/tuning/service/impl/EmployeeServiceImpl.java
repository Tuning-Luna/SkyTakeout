package com.tuning.service.impl;

import com.Tuning.context.BaseContext;
import com.Tuning.dto.*;
import com.Tuning.entity.Employee;
import com.Tuning.exception.BizException;
import com.Tuning.result.PageResult;
import com.Tuning.utils.JWTUtil;
import com.Tuning.utils.PasswordUtil;
import com.Tuning.vo.EmployeeLoginVO;
import com.Tuning.vo.EmployeeQueryVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tuning.mapper.EmployeeMapper;
import com.tuning.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "您无权登录");
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
    employee.setCreateUser(BaseContext.getCurrentId());
    employee.setUpdateUser(BaseContext.getCurrentId());

    Integer rows = employeeMapper.insertEmployee(employee);
    if (rows != 1) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "插入失败");
    }
    System.out.println("插入成功，新的id：" + employee.getId());
  }

  @Override
  public PageResult<EmployeeQueryVO> page(EmployeePageQueryDTO queryDTO) {
    PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());

    Page<EmployeeQueryVO> page = employeeMapper.pageQuery(queryDTO);
    long total = page.getTotal();
    List<EmployeeQueryVO> records = page.getResult();
    return new PageResult<>(total, records);
  }

  @Override
  public void updateStatusById(String id, Integer status) {
    Employee employee = Employee.builder()
            .id(Long.valueOf(id))
            .status(status)
            .updateUser(BaseContext.getCurrentId())
            .build();

    employeeMapper.updateEmployeeById(employee);
  }

  @Override
  public void updateById(EmployeeUpdateDTO employeeDTO) {
    // 1. 检查要更新的数据是否存在
    Employee e = employeeMapper.selectById(employeeDTO.getId());
    if (e == null) {
      throw new BizException(HttpStatus.BAD_REQUEST, "要修改的数据不存在");
    }

    // 2. 检查用户名是否重复（排除自己）
    Employee exist = employeeMapper.selectByUsername(employeeDTO.getUsername());
    if (exist != null && ! exist.getId().equals(employeeDTO.getId())) {
      throw new BizException(HttpStatus.CONFLICT, "用户名已存在");
    }

    // 3. 执行更新
    Employee newEmp = new Employee();
    BeanUtils.copyProperties(employeeDTO, newEmp);
    newEmp.setUpdateUser(BaseContext.getCurrentId());
    newEmp.setUpdateTime(LocalDateTime.now());

    int rows = employeeMapper.updateEmployeeById(newEmp);
    if (rows != 1) {
      throw new BizException(HttpStatus.NOT_ACCEPTABLE, "更新失败");
    }
  }


  @Override
  public EmployeeQueryVO getById(Long id) {
    Employee employee = employeeMapper.selectById(id);
    EmployeeQueryVO employeeQueryVO = new EmployeeQueryVO();
    BeanUtils.copyProperties(employee, employeeQueryVO);
    // 查到的Entity数据直接赋值给VO，因为字段都一样
    employeeQueryVO.setPassword("*******"); // 密码设为空
    return employeeQueryVO;
  }

  @Override
  public void upddatePassword(EmployeePasswordUpdateDTO dto) {
    Employee employee = employeeMapper.selectById(dto.getEmpId());
    if (employee == null) {
      throw new BizException(HttpStatus.BAD_REQUEST, "用户不存在");
    }

    if (! PasswordUtil.matches(dto.getOldPassword(), employee.getPassword())) {
      throw new BizException(HttpStatus.BAD_REQUEST, "旧密码不正确");
    }

    // 构建更新对象
    Employee updateEmployee = new Employee();
    updateEmployee.setId(dto.getEmpId());
    updateEmployee.setPassword(PasswordUtil.encrypt(dto.getNewPassword()));
    updateEmployee.setUpdateUser(BaseContext.getCurrentId());

    // 调用万能更新方法
    employeeMapper.updateEmployeeById(updateEmployee);
  }
}
