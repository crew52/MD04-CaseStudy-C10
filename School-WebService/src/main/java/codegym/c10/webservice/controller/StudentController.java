package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.StudentDTO;
import codegym.c10.webservice.model.service.studentservice.StudentServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private StudentServiceStudent studentService;

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className) {

        Page<StudentDTO> students;

        if (name != null || className != null) {
            students = studentService.searchStudents(name, className, PageRequest.of(page, size));
        } else {
            students = studentService.findAll(PageRequest.of(page, size));
        }

        return ResponseEntity.ok(students);
    }
}
