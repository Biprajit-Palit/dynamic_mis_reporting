package com.mis.student.specification;

import com.mis.student.dto.StudentSearchRequest;
import com.mis.student.entity.Student;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;

public final class StudentQuerySpec {

    private StudentQuerySpec() {
    }

    public static Specification<Student> fromRequest(StudentSearchRequest request) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (request.getFrom_date() != null && !request.getFrom_date().isBlank()) {
                predicate = cb.and(
                    predicate,
                    cb.greaterThanOrEqualTo(
                        root.get("admissionDatetime"),
                        LocalDate.parse(request.getFrom_date()).atStartOfDay()
                    )
                );
            }

            if (request.getSemester() != null) {
                predicate = cb.and(
                    predicate,
                    cb.equal(root.get("semester"), request.getSemester())
                );
            }

            if (request.getTo_date() != null && !request.getTo_date().isBlank()) {
                predicate = cb.and(
                    predicate,
                    cb.lessThanOrEqualTo(
                        root.get("admissionDatetime"),
                        LocalDate.parse(request.getTo_date()).atTime(LocalTime.MAX)
                    )
                );
            }

            if (request.getDepartment_id() != null) {
                predicate = cb.and(
                    predicate,
                    cb.equal(
                        root.get("department").get("departmentId"),
                        request.getDepartment_id()
                    )
                );
            }

            if (request.getCourse_id() != null) {
                query.distinct(true);

                Join<Object, Object> studentCourse = root.join("courses", JoinType.INNER);

                predicate = cb.and(
                    predicate,
                    cb.equal(
                        studentCourse.get("course").get("courseId"),
                        request.getCourse_id()
                    )
                );
            }

            if (request.getStudent_roll_no() != null
                && !request.getStudent_roll_no().isBlank()) {
                predicate = cb.and(
                    predicate,
                    cb.equal(root.get("studentRollNo"), request.getStudent_roll_no())
                );
            }

            if (request.getStudent_name() != null
                && !request.getStudent_name().isBlank()) {
                predicate = cb.and(
                    predicate,
                    cb.like(
                        cb.lower(root.get("studentName")),
                        request.getStudent_name().toLowerCase() + "%"
                    )
                );
            }

            return predicate;
        };
    }
}
