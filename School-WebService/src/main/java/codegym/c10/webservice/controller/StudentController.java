package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.StudentDTO;
import codegym.c10.webservice.model.service.studentservice.IStudentServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
public class StudentController {

    @Autowired
    private IStudentServiceStudent studentService;

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDTO> students = studentService.findAll(pageable);
        return ResponseEntity.ok(students);
    }

    // Các phương thức khác giữ nguyên như trước
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id) {
        StudentDTO student = studentService.findById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO savedStudent = studentService.save(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.update(id, studentDTO);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        StudentDTO student = studentService.findById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/class")
    public ResponseEntity<Page<StudentDTO>> searchByClassName(
            @RequestParam String className,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDTO> students = studentService.searchByClassName(className, pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search/name")
    public ResponseEntity<Page<StudentDTO>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDTO> students = studentService.searchByName(name, pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StudentDTO>> searchByClassNameAndName(
            @RequestParam String className,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDTO> students = studentService.searchByClassNameAndName(className, name, pageable);
        return ResponseEntity.ok(students);
    }
}