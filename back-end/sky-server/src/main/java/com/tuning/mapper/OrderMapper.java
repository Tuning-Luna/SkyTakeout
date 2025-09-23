package com.tuning.mapper;

import com.Tuning.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
  void insert(Orders order);
}
