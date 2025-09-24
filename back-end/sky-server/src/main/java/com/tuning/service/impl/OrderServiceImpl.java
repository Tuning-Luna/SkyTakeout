package com.tuning.service.impl;

import com.Tuning.context.BaseContext;
import com.Tuning.dto.OrdersPageQueryDTO;
import com.Tuning.dto.OrdersSubmitDTO;
import com.Tuning.entity.AddressBook;
import com.Tuning.entity.OrderDetail;
import com.Tuning.entity.Orders;
import com.Tuning.entity.ShoppingCart;
import com.Tuning.exception.BizException;
import com.Tuning.result.PageResult;
import com.Tuning.vo.OrderSubmitVO;
import com.Tuning.vo.OrderVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tuning.mapper.AddressBookMapper;
import com.tuning.mapper.OrderDetailMapper;
import com.tuning.mapper.OrderMapper;
import com.tuning.mapper.ShoppingCartMapper;
import com.tuning.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
  final private OrderMapper orderMapper;
  final private OrderDetailMapper orderDetailMapper;
  final private ShoppingCartMapper shoppingCartMapper;
  final private AddressBookMapper addressBookMapper;

  // 必须在有效 Spring Bean 中定义自动装配成员(@Component|@Service|…)
  @Autowired
  public OrderServiceImpl(OrderMapper orderMapper,
                          OrderDetailMapper orderDetailMapper,
                          ShoppingCartMapper shoppingCartMapper,
                          AddressBookMapper addressBookMapper) {
    this.orderMapper = orderMapper;
    this.orderDetailMapper = orderDetailMapper;
    this.shoppingCartMapper = shoppingCartMapper;
    this.addressBookMapper = addressBookMapper;
  }

  @Override
  public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
    // 1. 校验收货地址
    AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
    if (addressBook == null) {
      throw new BizException(HttpStatus.BAD_REQUEST, "地址异常");
    }

    Long userId = BaseContext.getCurrentId();

    // 2. 查询用户购物车
    ShoppingCart shoppingCart = new ShoppingCart();
    shoppingCart.setUserId(userId);
    List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
    if (shoppingCartList == null || shoppingCartList.isEmpty()) {
      throw new BizException(HttpStatus.BAD_REQUEST, "购物车为空");
    }

    // 3. 构造订单数据
    Orders order = new Orders();
    BeanUtils.copyProperties(ordersSubmitDTO, order);
    order.setUserId(userId);
    order.setPhone(addressBook.getPhone());
    order.setAddress(addressBook.getDetail());
    order.setConsignee(addressBook.getConsignee());
    order.setNumber(String.valueOf(System.currentTimeMillis()));
    order.setStatus(Orders.PENDING_PAYMENT);
    order.setPayStatus(Orders.UN_PAID);
    order.setOrderTime(LocalDateTime.now());

    // 4. 计算订单总金额
    BigDecimal totalAmount = shoppingCartList.stream()
            .map(cart -> cart.getAmount().multiply(new BigDecimal(cart.getNumber())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    order.setAmount(totalAmount);

    // 5. 插入订单表（生成订单ID）
    orderMapper.insert(order);

    // 6. 构造订单明细
    List<OrderDetail> orderDetailList = new ArrayList<>();
    for (ShoppingCart cart : shoppingCartList) {
      OrderDetail orderDetail = new OrderDetail();
      BeanUtils.copyProperties(cart, orderDetail);
      orderDetail.setOrderId(order.getId()); // 此时 orderId 已生成
      orderDetailList.add(orderDetail);
    }

    // 7. 批量插入订单明细
    orderDetailMapper.insertBatch(orderDetailList);

    // 8. 清空购物车
    shoppingCartMapper.deleteByUserId(userId);

    // 9. 封装返回结果
    OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
            .id(order.getId())
            .orderNumber(order.getNumber())
            .orderAmount(order.getAmount())
            .orderTime(order.getOrderTime())
            .build();

    return orderSubmitVO;
  }

  @Override
  public PageResult<OrderVO> pageQuery4User(int pageNum, int pageSize, Integer status) {
    // 设置分页
    PageHelper.startPage(pageNum, pageSize);

    OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
    ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
    ordersPageQueryDTO.setStatus(status);

    // 分页条件查询
    Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);

    List<OrderVO> list = new ArrayList<>();

    // 查询出订单明细，并封装入OrderVO进行响应
    if (page != null && page.getTotal() > 0) {
      for (Orders orders : page) {
        Long orderId = orders.getId();// 订单id

        // 查询订单明细
        List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetails);

        list.add(orderVO);
      }
    }
    return new PageResult<>(page.getTotal(), list);
  }


}
