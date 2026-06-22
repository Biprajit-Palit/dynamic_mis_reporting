package com.mis.dynamic_report.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dynamic_report")
@Getter
@Setter
public class ReportConfig {

    @Id
    @Column(name = "report_id")
    private Long reportId;

    private String reportName;

    @Column(columnDefinition = "jsonb")
    private String inputFilters;

    @Column(columnDefinition = "jsonb")
    private String outputColumns;

    @Column(name = "query", columnDefinition = "TEXT")
    private String query;
}
