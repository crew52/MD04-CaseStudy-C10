package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.AttendanceDTO;
import codegym.c10.webservice.model.dto.GradeDTO;
import codegym.c10.webservice.model.service.iface.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private IAttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<Page<AttendanceDTO>> getAllAttendances(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<AttendanceDTO>  attendanceDTOS = attendanceService.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(attendanceDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Integer id) {
        return attendanceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        return ResponseEntity.ok(attendanceService.save(attendanceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Integer id) {
        attendanceService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Integer id, @RequestBody AttendanceDTO attendanceDTO) {
        if (!attendanceService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        attendanceDTO.setId(id); // Đảm bảo ID được cập nhật đúng
        return ResponseEntity.ok(attendanceService.save(attendanceDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AttendanceDTO>> searchAttendances(
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String studentName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(attendanceService.searchAttendances(className, studentName, page, size));
    }

    // API cho Teacher
    @GetMapping("/teacher/{id}")
    public ResponseEntity<Page<AttendanceDTO>> getTeacherAttendances(
            @PathVariable("id") Integer teacherId,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String studentName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<AttendanceDTO> attendances = attendanceService.getTeacherAttendances(teacherId, className, studentName, page, size);
        return ResponseEntity.ok(attendances);
    }

}
