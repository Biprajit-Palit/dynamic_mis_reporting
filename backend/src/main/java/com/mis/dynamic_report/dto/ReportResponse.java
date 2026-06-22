package com.mis.dynamic_report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReportResponse {

    private Long reportId;
    private String reportName;
    private List<FilterFieldDto> inputFilters;
    private Object outputColumns;
}
