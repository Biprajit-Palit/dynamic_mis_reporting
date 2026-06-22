package com.mis.dynamic_report.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterFieldDto {

    private String name;
    private String type;
    private String label;
    private String dropdown_query;
    private String depends_on;
    private List<DropdownOptionDto> options;
}
