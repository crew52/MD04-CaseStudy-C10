package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.ClassesDTO;
import codegym.c10.webservice.model.service.studentservice.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin("*")
public class ClassesController {
    @Autowired
    private ClassesService classesService;

    @GetMapping
    public ResponseEntity<List<ClassesDTO>> getAllClasses() {
        List<ClassesDTO> classes = classesService.findAll();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesDTO> getClassById(@PathVariable Integer id) {
        ClassesDTO cls = classesService.findById(id);
        if (cls == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(cls);
    }


    @PostMapping
    public ResponseEntity<ClassesDTO> createClass(@RequestBody ClassesDTO classesDTO) {
        ClassesDTO savedClass = classesService.save(classesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClass);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id) {
        ClassesDTO cls = classesService.findById(id);
        if (cls == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        classesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
