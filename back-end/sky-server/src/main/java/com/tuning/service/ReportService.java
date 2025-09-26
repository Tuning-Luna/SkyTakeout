package com.tuning.service;

import com.Tuning.vo.OrderReportVO;
import com.Tuning.vo.SalesTop10ReportVO;
import com.Tuning.vo.TurnoverReportVO;
import com.Tuning.vo.UserReportVO;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public interface ReportService {
  TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);

  UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

  OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

  SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

  void exportBusinessData(HttpServletResponse response);
}
