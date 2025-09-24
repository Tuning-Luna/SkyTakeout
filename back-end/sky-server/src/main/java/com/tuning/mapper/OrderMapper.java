package com.tuning.mapper;

import com.Tuning.dto.OrdersPageQueryDTO;
import com.Tuning.entity.Orders;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
  void insert(Orders order);

  Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

  Orders getById(Long id);
}
