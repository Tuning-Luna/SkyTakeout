package com.tuning.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {
  Integer countByMap(Map<String, Object> map);
}
