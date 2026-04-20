package org.example.aidemo.studyroom.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.aidemo.common.model.Result;
import org.example.aidemo.studyroom.entity.ReportCountControllerVO;
import org.example.aidemo.studyroom.entity.ReportCountVO;
import org.example.aidemo.studyroom.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/count")
    public Result<ReportCountControllerVO> report(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        LocalDateTime startDateTime = startDate.atStartOfDay();

        List<ReportCountVO> reportCountVOList = reportService.getCount(startDateTime, endDateTime);

        List<String> dateList= new ArrayList<>();
        List<String> countList= new ArrayList<>();

        reportCountVOList.forEach(reportCountVO -> {
           dateList.add(reportCountVO.getOrderDate().toString());
           countList.add(reportCountVO.getOrderCount().toString());
        });
        ReportCountControllerVO reportCountControllerVO = new ReportCountControllerVO(String.join(",",dateList),String.join(",",countList));
        return Result.success(reportCountControllerVO);
    }
    @GetMapping("/export")
    public String export(LocalDate startDate, LocalDate endDate) {
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        return reportService.exportReport(startDateTime, endDateTime);
    }

    @GetMapping("/export/download")
    public void exportDownload(LocalDate startDate, LocalDate endDate,HttpServletResponse  response){
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        reportService.exportDownload(startDateTime, endDateTime,response);
    }
}
