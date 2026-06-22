package com.mis.student.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.mis.student.dto.StudentRecordResponse;
import com.mis.student.dto.StudentSearchRequest;
import com.mis.student.entity.Student;
import com.mis.student.entity.StudentCourse;
import com.mis.student.repository.StudentRepository;
import com.mis.student.specification.StudentQuerySpec;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Page<StudentRecordResponse> search(StudentSearchRequest request) {
        int page = request.getPage() == null ? 0 : request.getPage();
        int size = request.getSize() == null ? 10 : request.getSize();

        Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(
                Sort.Order.asc("department.departmentName"),
                Sort.Order.asc("semester"),
                Sort.Order.asc("studentRollNo")
            )
        );

        return studentRepository
            .findAll(StudentQuerySpec.fromRequest(request), pageable)
            .map(student -> toRecordResponse(student, request.getCourse_id()));
    }

    public byte[] exportCSV(StudentSearchRequest request) {
        try {
            List<Student> students = studentRepository.findAll(
                StudentQuerySpec.fromRequest(request)
            );

            sortStudents(students);

            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try (CSVPrinter printer = new CSVPrinter(
                new PrintWriter(output),
                CSVFormat.DEFAULT.builder()
                    .setHeader(
                        "Roll No",
                        "Student Name",
                        "Department",
                        "Course",
                        "Semester",
                        "Marks",
                        "Attendance"
                    )
                    .get()
            )) {
                for (Student student : students) {
                    StudentCourse enrollment = resolveEnrollment(
                        student,
                        request.getCourse_id()
                    );

                    printer.printRecord(
                        student.getStudentRollNo(),
                        student.getStudentName(),
                        student.getDepartment().getDepartmentName(),
                        enrollment == null ? "" : enrollment.getCourse().getCourseName(),
                        student.getSemester(),
                        enrollment == null ? "" : enrollment.getMarks(),
                        enrollment == null ? "" : enrollment.getAttendancePercentage()
                    );
                }

                printer.flush();
            }

            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] exportPDF(StudentSearchRequest request) {
        try {
            List<Student> students = studentRepository.findAll(
                StudentQuerySpec.fromRequest(request)
            );

            sortStudents(students);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(output);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            Table table = new Table(7);

            String[] headers = {
                "Roll No", "Name", "Department", "Course",
                "Semester", "Marks", "Attendance"
            };

            for (String header : headers) {
                table.addCell(header);
            }

            for (Student student : students) {
                StudentCourse enrollment = resolveEnrollment(
                    student,
                    request.getCourse_id()
                );

                table.addCell(student.getStudentRollNo());
                table.addCell(student.getStudentName());
                table.addCell(student.getDepartment().getDepartmentName());
                table.addCell(
                    enrollment == null ? "" : enrollment.getCourse().getCourseName()
                );
                table.addCell(String.valueOf(student.getSemester()));
                table.addCell(
                    enrollment == null ? "" : String.valueOf(enrollment.getMarks())
                );
                table.addCell(
                    enrollment == null
                        ? ""
                        : String.valueOf(enrollment.getAttendancePercentage())
                );
            }

            document.add(table);
            document.close();

            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sortStudents(List<Student> students) {
        students.sort(
            Comparator
                .comparing((Student s) -> s.getDepartment().getDepartmentName())
                .thenComparing(Student::getSemester)
                .thenComparing(Student::getStudentRollNo)
                .thenComparing(
                    s -> s.getCourses().stream()
                        .map(StudentCourse::getMarks)
                        .max(Double::compareTo)
                        .orElse(0.0),
                    Comparator.reverseOrder()
                )
        );
    }

    private StudentCourse resolveEnrollment(Student student, Long courseId) {
        if (student.getCourses() == null || student.getCourses().isEmpty()) {
            return null;
        }

        if (courseId != null) {
            return student.getCourses().stream()
                .filter(enrollment ->
                    enrollment.getCourse() != null
                        && courseId.equals(enrollment.getCourse().getCourseId())
                )
                .findFirst()
                .orElse(null);
        }

        return student.getCourses().get(0);
    }

    private StudentRecordResponse toRecordResponse(Student student, Long courseId) {
        StudentCourse enrollment = resolveEnrollment(student, courseId);

        return StudentRecordResponse.builder()
            .student_roll_no(student.getStudentRollNo())
            .student_name(student.getStudentName())
            .department_name(student.getDepartment().getDepartmentName())
            .course_name(
                enrollment == null ? null : enrollment.getCourse().getCourseName()
            )
            .semester(student.getSemester())
            .marks(enrollment == null ? null : enrollment.getMarks())
            .attendance_percentage(
                enrollment == null ? null : enrollment.getAttendancePercentage()
            )
            .admission_datetime(student.getAdmissionDatetime())
            .build();
    }
}
