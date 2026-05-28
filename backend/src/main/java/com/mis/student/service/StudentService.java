package com.mis.student.service;

import com.mis.course.entity.Course;
import com.mis.course.repository.CourseRepository;
import com.mis.department.entity.Department;
import com.mis.department.repository.DepartmentRepository;
import com.mis.student.dto.*;
import com.mis.student.entity.Student;
import com.mis.student.repository.StudentRepository;
import com.mis.student.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final DepartmentRepository departmentRepository;

    private final CourseRepository courseRepository;

    public Page<StudentResponseDTO> search(
            StudentFilterDTO dto
    ) {

        Pageable pageable = PageRequest.of(
                dto.getPage(),
                dto.getSize(),
                Sort.by("studentId").descending()
        );

        return studentRepository
                .findAll(
                        StudentSpecification.filter(dto),
                        pageable
                )
                .map(this::mapToDTO);
    }

    private StudentResponseDTO mapToDTO(
            Student student
    ) {

        Department department =
                departmentRepository
                        .findById(student.getDepartmentId())
                        .orElse(null);

        Course course =
                courseRepository
                        .findById(student.getCourseId())
                        .orElse(null);

        return StudentResponseDTO.builder()
                .student_roll_no(student.getStudentRollNo())
                .student_name(student.getStudentName())
                .department_name(
                        department != null
                                ? department.getDepartmentName()
                                : ""
                )
                .course_name(
                        course != null
                                ? course.getCourseName()
                                : ""
                )
                .marks(student.getMarks())
                .build();
    }
}