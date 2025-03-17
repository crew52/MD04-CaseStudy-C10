package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.dto.AttendanceDTO;
import codegym.c10.webservice.model.service.iface.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private IAttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendances() {
        return ResponseEntity.ok((List<AttendanceDTO>) attendanceService.findAll());
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
}
