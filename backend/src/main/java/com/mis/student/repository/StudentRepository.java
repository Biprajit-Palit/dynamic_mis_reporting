package com.mis.student.repository;

import com.mis.student.entity.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends
        JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {
}