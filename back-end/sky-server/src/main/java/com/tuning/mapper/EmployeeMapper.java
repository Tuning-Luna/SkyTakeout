package com.tuning.mapper;


import com.Tuning.OperationType;
import com.Tuning.dto.EmployeePageQueryDTO;
import com.Tuning.entity.Employee;
import com.Tuning.vo.EmployeeQueryVO;
import com.github.pagehelper.Page;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper {
  // 通过username查询id, name, username, password,status
  Employee selectByUsername(String username);

  // 判断数据库中 username 是否存在
  boolean existsByUsername(@Param("username") String username);

  // 插入员工
  @AutoFill(value = OperationType.INSERT)
  Integer insertEmployee(Employee employee);

  // 根据page和name分页筛选返回的数据
  Page<EmployeeQueryVO> pageQuery(EmployeePageQueryDTO queryDTO);

  // 根据id更新一个员工信息
  @AutoFill(value = OperationType.UPDATE)
  Integer updateEmployeeById(Employee employee);

  // 根据id查询表中所有数据
  Employee selectById(Long id);
}
