package com.mis.dynamic_report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DropdownOptionDto {

    private Object value;
    private String label;
    private Long parentId;
}
