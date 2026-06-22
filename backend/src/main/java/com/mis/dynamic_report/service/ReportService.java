package com.mis.dynamic_report.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mis.dynamic_report.dto.DropdownOptionDto;
import com.mis.dynamic_report.dto.FilterFieldDto;
import com.mis.dynamic_report.dto.ReportResponse;
import com.mis.dynamic_report.entity.ReportConfig;
import com.mis.dynamic_report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String COURSE_DROPDOWN_QUERY =
        "SELECT course_id AS value, course_name AS label, department_id AS department_id "
            + "FROM course ORDER BY course_name";

    private final ReportRepository reportRepository;
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public ReportResponse getReport(Long reportId) throws Exception {
        ReportConfig reportConfig = reportRepository.findById(reportId).orElseThrow();

        List<FilterFieldDto> filterFields = objectMapper.readValue(
            reportConfig.getInputFilters(),
            new TypeReference<>() {}
        );

        for (FilterFieldDto field : filterFields) {
            if ("dropdown".equals(field.getType())) {
                prepareCourseDropdown(field);

                List<DropdownOptionDto> options = jdbcTemplate.query(
                    field.getDropdown_query(),
                    (rs, rowNum) -> mapDropdownOption(rs)
                );

                field.setOptions(options);
            }
        }

        return ReportResponse.builder()
            .reportId(reportConfig.getReportId())
            .reportName(reportConfig.getReportName())
            .inputFilters(filterFields)
            .outputColumns(
                objectMapper.readValue(reportConfig.getOutputColumns(), Object.class)
            )
            .build();
    }

    private void prepareCourseDropdown(FilterFieldDto field) {
        if ("course_id".equals(field.getName())) {
            field.setDepends_on("department_id");
            field.setDropdown_query(COURSE_DROPDOWN_QUERY);
        }
    }

    private DropdownOptionDto mapDropdownOption(ResultSet rs) throws SQLException {
        DropdownOptionDto option = new DropdownOptionDto();
        option.setValue(rs.getObject("value"));
        option.setLabel(rs.getString("label"));

        if (hasColumn(rs, "department_id")) {
            Object parentId = rs.getObject("department_id");
            if (parentId instanceof Number number) {
                option.setParentId(number.longValue());
            }
        }

        return option;
    }

    private boolean hasColumn(ResultSet rs, String column) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            if (column.equalsIgnoreCase(meta.getColumnLabel(i))) {
                return true;
            }
        }
        return false;
    }
}
