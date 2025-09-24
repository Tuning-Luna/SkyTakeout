package com.tuning.mapper;

import com.Tuning.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
  void insertBatch(List<OrderDetail> orderDetails);

  List<OrderDetail> getByOrderId(Long orderId);
}
