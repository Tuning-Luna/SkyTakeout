package com.tuning.service.impl;

import com.Tuning.entity.Orders;
import com.Tuning.vo.BusinessDataVO;
import com.Tuning.vo.DishOverViewVO;
import com.Tuning.vo.OrderOverViewVO;
import com.Tuning.vo.SetmealOverViewVO;
import com.tuning.mapper.DishMapper;
import com.tuning.mapper.OrderMapper;
import com.tuning.mapper.SetmealMapper;
import com.tuning.mapper.UserMapper;
import com.tuning.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
  final private OrderMapper orderMapper;
  final private UserMapper userMapper;
  final private DishMapper dishMapper;
  final private SetmealMapper setmealMapper;

  @Autowired
  public WorkspaceServiceImpl(OrderMapper orderMapper,
                              UserMapper userMapper,
                              DishMapper dishMapper,
                              SetmealMapper setmealMapper) {
    this.orderMapper = orderMapper;
    this.userMapper = userMapper;
    this.dishMapper = dishMapper;
    this.setmealMapper = setmealMapper;
  }


  @Override
  public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
    /**
     * 营业额：当日已完成订单的总金额
     * 有效订单：当日已完成订单的数量
     * 订单完成率：有效订单数 / 总订单数
     * 平均客单价：营业额 / 有效订单数
     * 新增用户：当日新增用户的数量
     */

    Map<String, Object> map = new HashMap();
    map.put("begin", begin);
    map.put("end", end);

    // 查询总订单数
    Integer totalOrderCount = orderMapper.countByMap(map);

    map.put("status", Orders.COMPLETED);
    // 营业额
    Double turnover = orderMapper.sumByMap(map);
    turnover = turnover == null ? 0.0 : turnover;

    // 有效订单数
    Integer validOrderCount = orderMapper.countByMap(map);

    double unitPrice = 0.0;

    double orderCompletionRate = 0.0;
    if (totalOrderCount != 0 && validOrderCount != 0) {
      // 订单完成率
      orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
      // 平均客单价
      unitPrice = turnover / validOrderCount;
    }

    // 新增用户数
    Integer newUsers = userMapper.countByMap(map);

    return BusinessDataVO.builder()
            .turnover(turnover)
            .validOrderCount(validOrderCount)
            .orderCompletionRate(orderCompletionRate)
            .unitPrice(unitPrice)
            .newUsers(newUsers)
            .build();
  }

  @Override
  public OrderOverViewVO getOrderOverView() {
    Map<String, Object> map = new HashMap<>();
    map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
    map.put("status", Orders.TO_BE_CONFIRMED);

    // 待接单
    Integer waitingOrders = orderMapper.countByMap(map);

    // 待派送
    map.put("status", Orders.CONFIRMED);
    Integer deliveredOrders = orderMapper.countByMap(map);

    // 已完成
    map.put("status", Orders.COMPLETED);
    Integer completedOrders = orderMapper.countByMap(map);

    // 已取消
    map.put("status", Orders.CANCELLED);
    Integer cancelledOrders = orderMapper.countByMap(map);

    // 全部订单
    map.put("status", null);
    Integer allOrders = orderMapper.countByMap(map);

    return OrderOverViewVO.builder()
            .waitingOrders(waitingOrders)
            .deliveredOrders(deliveredOrders)
            .completedOrders(completedOrders)
            .cancelledOrders(cancelledOrders)
            .allOrders(allOrders)
            .build();
  }

  @Override
  public DishOverViewVO getDishOverView() {
    Map<String, Object> map = new HashMap<>();
    map.put("status", 1);
    Integer sold = dishMapper.countByMap(map);

    map.put("status", 2);
    Integer discontinued = dishMapper.countByMap(map);

    return DishOverViewVO.builder()
            .sold(sold)
            .discontinued(discontinued)
            .build();
  }

  @Override
  public SetmealOverViewVO getSetmealOverView() {
    Map<String, Object> map = new HashMap<>();
    map.put("status", 1);
    Integer sold = setmealMapper.countByMap(map);

    map.put("status", 2);
    Integer discontinued = setmealMapper.countByMap(map);

    return SetmealOverViewVO.builder()
            .sold(sold)
            .discontinued(discontinued)
            .build();
  }
}
