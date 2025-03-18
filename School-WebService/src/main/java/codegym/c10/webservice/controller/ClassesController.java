package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.ClassesDTO;
import codegym.c10.webservice.model.service.studentservice.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class ClassesController {

    @Autowired
    private IClassService classService;

    @GetMapping
    public ResponseEntity<List<ClassesDTO>> getAll() {
        List<ClassesDTO> classes = classService.findAll();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ClassesDTO>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String className) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClassesDTO> classesPage;
        if (className != null && !className.isEmpty()) {
            classesPage = classService.findByClassName(className, pageable); // Gọi phương thức tìm kiếm
        } else {
            classesPage = classService.findAll(pageable); // Lấy tất cả nếu không có className
        }
        return ResponseEntity.ok(classesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassesDTO> getById(@PathVariable Integer id) {
        try {
            ClassesDTO classesDTO = classService.findById(id);
            return ResponseEntity.ok(classesDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ClassesDTO> create(@RequestBody ClassesDTO dto) {
        try {
            ClassesDTO createdClass = classService.save(dto);
            return new ResponseEntity<>(createdClass, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ClassesDTO(null, e.getMessage(), null, null, null), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassesDTO> update(@PathVariable Integer id, @RequestBody ClassesDTO dto) {
        try {
            ClassesDTO updatedClass = classService.update(id, dto);
            return ResponseEntity.ok(updatedClass);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            classService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}