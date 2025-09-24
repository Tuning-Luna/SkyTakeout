package com.tuning.service;

import com.Tuning.dto.*;
import com.Tuning.result.PageResult;
import com.Tuning.vo.OrderStatisticsVO;
import com.Tuning.vo.OrderSubmitVO;
import com.Tuning.vo.OrderVO;

public interface OrderService {
  OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

  PageResult<OrderVO> pageQuery4User(int pageNum, int pageSize, Integer status);

  OrderVO details(Long id);

  void userCancelById(Long id);

  void repetition(Long id);

  PageResult<OrderVO> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

  OrderStatisticsVO statistics();

  void confirm(OrdersConfirmDTO ordersConfirmDTO);

  void rejection(OrdersRejectionDTO ordersRejectionDTO);

  void cancel(OrdersCancelDTO ordersCancelDTO);
}
