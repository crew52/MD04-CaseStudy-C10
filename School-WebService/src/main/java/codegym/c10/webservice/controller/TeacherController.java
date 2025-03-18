package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.TeacherUserRequestDTO;
import codegym.c10.webservice.model.eNum.SubjectEnum;
import codegym.c10.webservice.model.entity.Teacher;
import codegym.c10.webservice.model.eNum.Gender;
import codegym.c10.webservice.model.entity.User;
import codegym.c10.webservice.model.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin("*")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public ResponseEntity<Page<Teacher>> getTeachers(
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String directionStr) {
        Sort.Direction direction = (directionStr != null) ? Sort.Direction.valueOf(directionStr.toUpperCase()) : null;
        Page<Teacher> teachers = teacherService.findAllPaged(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PostMapping("/create-with-user")
    public ResponseEntity<Teacher> createTeacherWithUser(@RequestBody TeacherUserRequestDTO request) {
        try {
            Teacher teacher = request.getTeacher();
            User user = request.getUser();
            Teacher savedTeacher = teacherService.createTeacherWithUser(teacher, user);
            return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // 409 nếu username trùng
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") Integer id) {
        return teacherService.findById(id)
                .map(teacher -> new ResponseEntity<>(teacher, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.save(teacher);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Integer id, @RequestBody Teacher teacher) {
        return teacherService.findById(id)
                .map(existing -> {
                    teacher.setId(id);
                    Teacher updatedTeacher = teacherService.save(teacher);
                    return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Integer id) {
        return teacherService.findById(id)
                .map(teacher -> {
                    teacherService.remove(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/by-user/{userId}")
    public ResponseEntity<Teacher> updateTeacherByUserId(
            @PathVariable("userId") Integer userId,
            @RequestBody Teacher teacher) {
        try {
            Teacher updatedTeacher = teacherService.updateByUserId(userId, teacher);
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint mới: Tìm kiếm giáo viên theo name, gender, subject
    @GetMapping("/search")
    public ResponseEntity<Page<Teacher>> searchTeachers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "subjectName", required = false) SubjectEnum subjectName,
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String directionStr) {
        Sort.Direction direction = (directionStr != null) ? Sort.Direction.valueOf(directionStr.toUpperCase()) : null;
        Page<Teacher> teachers = teacherService.searchTeachers(name, gender, subjectName, pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

//    // Endpoint mới: Lấy danh sách tên giáo viên
//    @GetMapping("/names")
//    public ResponseEntity<List<String>> getTeacherNames() {
//        List<String> teacherNames = teacherService.findAllTeacherNames();
//        return new ResponseEntity<>(teacherNames, HttpStatus.OK);
//    }
}