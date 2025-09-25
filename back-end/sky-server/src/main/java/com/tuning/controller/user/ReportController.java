package com.tuning.controller.user;

import com.Tuning.result.ApiResult;
import com.Tuning.vo.OrderReportVO;
import com.Tuning.vo.TurnoverReportVO;
import com.Tuning.vo.UserReportVO;
import com.tuning.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
public class ReportController {

  final private ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping("/turnoverStatistics")
  public ApiResult<TurnoverReportVO> turnoverStatistics(
          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

    return ApiResult.ok(reportService.getTurnover(begin, end));
  }

  @GetMapping("/userStatistics")
  public ApiResult<UserReportVO> userStatistics(
          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

    return ApiResult.ok(reportService.getUserStatistics(begin, end));
  }

  @GetMapping("/ordersStatistics")
  public ApiResult<OrderReportVO> orderStatistics(
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          LocalDate begin,
          @DateTimeFormat(pattern = "yyyy-MM-dd")
          LocalDate end) {

    return ApiResult.ok(reportService.getOrderStatistics(begin, end));
  }
}
