package com.mis.student.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDTO {

    private String student_roll_no;

    private String student_name;

    private String department_name;

    private String course_name;

    private Double marks;
}