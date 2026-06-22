package com.mis.student.controller;

import com.mis.student.dto.StudentRecordResponse;
import com.mis.student.dto.StudentSearchRequest;
import com.mis.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/search")
    public Page<StudentRecordResponse> search(@RequestBody StudentSearchRequest request) {
        return studentService.search(request);
    }

    @PostMapping("/export/csv")
    public ResponseEntity<byte[]> exportCSV(@RequestBody StudentSearchRequest request) {
        byte[] file = studentService.exportCSV(request);

        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=students.csv")
            .header("Content-Type", "text/csv")
            .body(file);
    }

    @PostMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPDF(@RequestBody StudentSearchRequest request) {
        byte[] file = studentService.exportPDF(request);

        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=students.pdf")
            .header("Content-Type", "application/pdf")
            .body(file);
    }
}
