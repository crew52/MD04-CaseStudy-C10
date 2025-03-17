package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.AttendanceDTO;
import codegym.c10.webservice.model.dto.GradeDTO;
import codegym.c10.webservice.model.service.iface.IAttendanceService;
import codegym.c10.webservice.model.service.iface.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {
    @Autowired
    private IGradeService iGradeService;

    @GetMapping
    public ResponseEntity<List<GradeDTO>> getAllGrades() {
        return ResponseEntity.ok((List<GradeDTO>) iGradeService.findAll());
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
}
