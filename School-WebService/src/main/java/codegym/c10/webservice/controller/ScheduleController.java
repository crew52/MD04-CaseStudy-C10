// ScheduleController.java
package codegym.c10.webservice.controller;

import codegym.c10.webservice.model.entity.Schedule;
import codegym.c10.webservice.model.service.iface.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:63342") // CHO PHÉP TRUY CẬP TỪ CLIENT
@RequestMapping("/schedules")
public class ScheduleController {
    @Autowired
    private IScheduleService scheduleService;

    @GetMapping
    public Iterable<Schedule> getAllSchedules() {
        return scheduleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Integer id) {
        Optional<Schedule> schedule = scheduleService.findById(id);
        return schedule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Schedule createSchedule(@RequestBody Schedule schedule) {
        return scheduleService.save(schedule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Integer id, @RequestBody Schedule schedule) {
        Optional<Schedule> existingSchedule = scheduleService.findById(id);
        if (existingSchedule.isPresent()) {
            schedule.setId(id);
            return ResponseEntity.ok(scheduleService.save(schedule));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        scheduleService.remove(id);
        return ResponseEntity.noContent().build();
    }

    // tim kiem theo id cua teacher
    @GetMapping("/teacher/{teacherId}")
    public List<Schedule> getSchedulesByTeacher(@PathVariable Integer teacherId) {
        return scheduleService.findByTeacherId(teacherId);
    }

    // tim kiem theo ten cua teacher
    @GetMapping("/teacher/name/{teacherName}")
    public List<Schedule> getSchedulesByTeacherName(@PathVariable String teacherName) {
        return scheduleService.findByTeacherName(teacherName);
    }
}