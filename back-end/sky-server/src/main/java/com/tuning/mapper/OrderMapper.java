package com.tuning.mapper;

import com.Tuning.dto.OrdersPageQueryDTO;
import com.Tuning.entity.Orders;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
  void insert(Orders order);

  Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

  Orders getById(Long id);

  void update(Orders orders);

  Integer countStatus(Integer status);

  List<Orders> getByStatusAndOrderTimeLT(@Param("status") Integer status,
                                         @Param("orderTime") LocalDateTime orderTime);
}
