package com.tuning.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ReportMapper {

  Double sumByMap(Map<String, Object> map);
}
