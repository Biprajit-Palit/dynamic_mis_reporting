package com.mis.student.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "student_roll_no")
    private String studentRollNo;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "course_id")
    private Long courseId;

    private Integer semester;

    private Double marks;

    @Column(name = "attendance_percentage")
    private Double attendancePercentage;

    @Column(name = "admission_datetime")
    private LocalDateTime admissionDatetime;
}