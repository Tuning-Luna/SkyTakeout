package com.tuning.mapper;


import com.Tuning.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
  Employee selectByUsername(String username);
}
