package com.mis.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSearchRequest {

    private String from_date;
    private String to_date;
    private Long course_id;
    private Long department_id;
    private String student_roll_no;
    private String student_name;
    private Integer semester;
    private Integer page;
    private Integer size;
}
