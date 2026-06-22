package com.mis.dynamic_report.controller;

import com.mis.dynamic_report.dto.ReportResponse;
import com.mis.dynamic_report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{id}")
    public ReportResponse getReport(@PathVariable Long id) throws Exception {
        return reportService.getReport(id);
    }
}
