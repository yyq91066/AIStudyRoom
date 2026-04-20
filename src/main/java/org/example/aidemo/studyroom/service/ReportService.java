package org.example.aidemo.studyroom.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.aidemo.studyroom.entity.ReportCountVO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<ReportCountVO> getCount(LocalDateTime startDate, LocalDateTime endDate);
    String exportReport(LocalDateTime startDate, LocalDateTime endDate);
    void exportDownload(LocalDateTime startDate, LocalDateTime endDate, HttpServletResponse  response);
}
