package com.mis.student.repository;

import com.mis.student.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {


    @Override
    @EntityGraph(
            attributePaths = {
                    "department",
                    "courses",
                    "courses.course"
            }
    )
    Page<Student> findAll(
            Specification<Student> spec,
            Pageable pageable
    );



    @Override
    @EntityGraph(
            attributePaths = {
                    "department",
                    "courses",
                    "courses.course"
            }
    )
    List<Student> findAll(
            Specification<Student> spec
    );
}