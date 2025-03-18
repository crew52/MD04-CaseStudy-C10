package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.AttendanceDTO;
import codegym.c10.webservice.model.dto.GradeDTO;
import codegym.c10.webservice.model.service.iface.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*")
public class GradeController {
    @Autowired
    private IGradeService iGradeService;

    @GetMapping
    public ResponseEntity<Page<GradeDTO>> getAllGrades(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Page<GradeDTO> gradePage = iGradeService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(gradePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getGradeById(@PathVariable Integer id) {
        return iGradeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GradeDTO> createGrade(@RequestBody GradeDTO gradeDTO) {
        return ResponseEntity.ok(iGradeService.save(gradeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Integer id) {
        iGradeService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable Integer id, @RequestBody GradeDTO gradeDTO) {
        if (!iGradeService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        gradeDTO.setId(id); // Đảm bảo ID được cập nhật đúng
        return ResponseEntity.ok(iGradeService.save(gradeDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GradeDTO>> searchAttendances(
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String examType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(iGradeService.searchGrades(className, studentName, subjectName,examType, page, size));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<Page<GradeDTO>> getTeacherAttendances(
            @PathVariable("id") Integer teacherId,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String examType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<GradeDTO> gradeDTOS = iGradeService.searchGradesByTeacher(teacherId, className, studentName, subjectName, examType, page, size);
        return ResponseEntity.ok(gradeDTOS);
    }
}
