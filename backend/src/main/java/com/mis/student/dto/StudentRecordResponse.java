package com.mis.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRecordResponse {

    private String student_roll_no;
    private String student_name;
    private String department_name;
    private String course_name;
    private Integer semester;
    private Double marks;
    private Double attendance_percentage;
    private LocalDateTime admission_datetime;
}
