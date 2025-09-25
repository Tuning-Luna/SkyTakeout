package com.tuning.service.impl;

import com.Tuning.entity.Orders;
import com.Tuning.vo.TurnoverReportVO;
import com.tuning.mapper.OrderMapper;
import com.tuning.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

  final private OrderMapper orderMapper;

  public ReportServiceImpl(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  @Override
  public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = new ArrayList<>();
    dateList.add(begin);

    while (! begin.equals(end)) {
      begin = begin.plusDays(1);// 日期计算，获得指定日期后1天的日期
      dateList.add(begin);
    }

    List<Double> turnoverList = new ArrayList<>();
    for (LocalDate date : dateList) {
      // 得到当前从00：00：00开始
      LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
      // 得到当前以23：59：59结束
      LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
      Map<String, Object> map = new HashMap<>();
      map.put("status", Orders.COMPLETED); // 只统计完成的订单数据
      map.put("begin", beginTime);
      map.put("end", endTime);
      Double turnover = orderMapper.sumByMap(map);
      turnover = turnover == null ? 0.0 : turnover;
      turnoverList.add(turnover);
    }

    // 封装成逗号分割的字符串返回
    return TurnoverReportVO.builder()
            .dateList(dateList.stream()
                    .map(LocalDate::toString)
                    .collect(Collectors.joining(",")))
            .turnoverList(turnoverList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            .build();

  }
}
