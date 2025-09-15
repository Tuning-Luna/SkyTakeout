package com.tuning.mapper;


import com.Tuning.dto.EmployeePageQueryDTO;
import com.Tuning.entity.Employee;
import com.Tuning.vo.EmployeePageQueryVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmployeeMapper {
  // 通过username查询id, name, username, password
  Employee selectByUsername(String username);

  // 判断数据库中 username 是否存在
  boolean existsByUsername(@Param("username") String username);

  // 插入员工
  Integer insertEmployee(Employee employee);

  Page<EmployeePageQueryVO> pageQuery(EmployeePageQueryDTO queryDTO);
}
