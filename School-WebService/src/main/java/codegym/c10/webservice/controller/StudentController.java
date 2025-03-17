package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.entity.Student;
import codegym.c10.webservice.model.service.studentservice.StudentServiceStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/students")
public class StudentController {

    @Autowired
    private StudentServiceStudent studentService;




    //_____hiển thị tất cả danh sách _____
    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        return ResponseEntity.ok(studentService.getAll());
    }


    //_____tìm kiếm theo tên _____
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name){
        return ResponseEntity.ok(studentService.searchByName(name));
    }


    //_____them du lieu_____
    @PostMapping("")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return ResponseEntity.ok(studentService.add(student));

    }


    //____ cap nhap lai nguoi dung theo id _____
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student){
        return ResponseEntity.ok(studentService.update(id, student));
    }


    //_____xoa nguoi dung theo id________
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id){
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }


    //______tìm kiếm theo id______
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id){
        return ResponseEntity.ok(studentService.getById(id));
    }






}
