package com.mis.dynamic_report.repository;

import com.mis.dynamic_report.entity.ReportConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportConfig, Long> {
}
