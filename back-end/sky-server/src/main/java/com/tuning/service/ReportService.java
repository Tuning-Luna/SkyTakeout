package com.tuning.service;

import com.Tuning.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {
  TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);
}
