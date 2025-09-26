package com.tuning.service.impl;

import com.Tuning.dto.GoodsSalesDTO;
import com.Tuning.entity.Orders;
import com.Tuning.vo.*;
import com.tuning.mapper.OrderMapper;
import com.tuning.mapper.UserMapper;
import com.tuning.service.ReportService;
import com.tuning.service.WorkspaceService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
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
  final private UserMapper userMapper;

  final private WorkspaceService workspaceService;


  public ReportServiceImpl(OrderMapper orderMapper,
                           UserMapper userMapper,
                           WorkspaceService workspaceService) {
    this.orderMapper = orderMapper;
    this.userMapper = userMapper;
    this.workspaceService = workspaceService;
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

  @Override
  public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = new ArrayList<>();
    dateList.add(begin);

    while (! begin.equals(end)) {
      begin = begin.plusDays(1);
      dateList.add(begin);
    }
    List<Integer> newUserList = new ArrayList<>(); // 新增用户数
    List<Integer> totalUserList = new ArrayList<>(); // 总用户数

    for (LocalDate date : dateList) {
      LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
      LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
      // 新增用户数量 select count(id) from user where create_time > ? and create_time < ?
      Integer newUser = getUserCount(beginTime, endTime);
      // 总用户数量 select count(id) from user where  create_time < ?
      Integer totalUser = getUserCount(null, endTime);

      newUserList.add(newUser);
      totalUserList.add(totalUser);
    }

    return UserReportVO.builder()
            .dateList(dateList.stream().map(String::valueOf).collect(Collectors.joining(",")))
            .newUserList(newUserList.stream().map(String::valueOf).collect(Collectors.joining(",")))
            .totalUserList(totalUserList.stream().map(String::valueOf).collect(Collectors.joining(",")))
            .build();

  }

  @Override
  public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
    List<LocalDate> dateList = new ArrayList<>();
    dateList.add(begin);

    while (! begin.equals(end)) {
      begin = begin.plusDays(1);
      dateList.add(begin);
    }
    // 每天订单总数集合
    List<Integer> orderCountList = new ArrayList<>();
    // 每天有效订单数集合
    List<Integer> validOrderCountList = new ArrayList<>();
    for (LocalDate date : dateList) {
      LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
      LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
      // 查询每天的总订单数 select count(id) from orders where order_time > ? and order_time < ?
      Integer orderCount = getOrderCount(beginTime, endTime, null);

      // 查询每天的有效订单数 select count(id) from orders where order_time > ? and order_time < ? and status = ?
      Integer validOrderCount = getOrderCount(beginTime, endTime, Orders.COMPLETED);

      orderCountList.add(orderCount);
      validOrderCountList.add(validOrderCount);
    }

    // 时间区间内的总订单数
    Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
    // 时间区间内的总有效订单数
    Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();
    // 订单完成率
    Double orderCompletionRate = 0.0;
    if (totalOrderCount != 0) {
      orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
    }
    return OrderReportVO.builder()
            .dateList(dateList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            .orderCountList(orderCountList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            .validOrderCountList(validOrderCountList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            .totalOrderCount(totalOrderCount)
            .validOrderCount(validOrderCount)
            .orderCompletionRate(orderCompletionRate)
            .build();

  }

  @Override
  public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
    LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
    LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
    List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);

    String nameList = goodsSalesDTOList.stream()
            .map(GoodsSalesDTO::getName)
            .map(String::valueOf) // 保险起见，避免 null 报错
            .collect(Collectors.joining(","));

    String numberList = goodsSalesDTOList.stream()
            .map(GoodsSalesDTO::getNumber)
            .map(String::valueOf)
            .collect(Collectors.joining(","));

    return SalesTop10ReportVO.builder()
            .nameList(nameList)
            .numberList(numberList)
            .build();

  }

  @Override
  public void exportBusinessData(HttpServletResponse response) {
    LocalDate begin = LocalDate.now().minusDays(30);
    LocalDate end = LocalDate.now().minusDays(1);
    // 查询概览运营数据，提供给Excel模板文件
    BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
    try {
      // 基于提供好的模板文件创建一个新的Excel表格对象
      XSSFWorkbook excel = null;
      if (inputStream != null) {
        excel = new XSSFWorkbook(inputStream);
      }
      // 获得Excel文件中的一个Sheet页
      XSSFSheet sheet = excel.getSheet("Sheet1");

      sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
      // 获得第4行
      XSSFRow row = sheet.getRow(3);
      // 获取单元格
      row.getCell(2).setCellValue(businessData.getTurnover());
      row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
      row.getCell(6).setCellValue(businessData.getNewUsers());
      row = sheet.getRow(4);
      row.getCell(2).setCellValue(businessData.getValidOrderCount());
      row.getCell(4).setCellValue(businessData.getUnitPrice());
      for (int i = 0; i < 30; i++) {
        LocalDate date = begin.plusDays(i);
        // 准备明细数据
        businessData = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
        row = sheet.getRow(7 + i);
        row.getCell(1).setCellValue(date.toString());
        row.getCell(2).setCellValue(businessData.getTurnover());
        row.getCell(3).setCellValue(businessData.getValidOrderCount());
        row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
        row.getCell(5).setCellValue(businessData.getUnitPrice());
        row.getCell(6).setCellValue(businessData.getNewUsers());
      }
      // 通过输出流将文件下载到客户端浏览器中
      ServletOutputStream out = response.getOutputStream();
      excel.write(out);
      // 关闭资源
      out.flush();
      out.close();
      excel.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
    Map<String, Object> map = new HashMap();
    map.put("status", status);
    map.put("begin", beginTime);
    map.put("end", endTime);
    return orderMapper.countByMap(map);
  }

  private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
    Map<String, Object> map = new HashMap<>();
    map.put("begin", beginTime);
    map.put("end", endTime);
    return userMapper.countByMap(map);
  }
}
