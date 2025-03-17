package codegym.c10.webservice.controller;


import codegym.c10.webservice.model.Schedule;
import codegym.c10.webservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    //    @GetMapping("/class/{classId}")
//    public List<Schedule> getByClassId(@PathVariable String classId) {
//        return scheduleService.getScheduleByClassId(classId);
//    }
//
//    @GetMapping("/teacher/{teacherId}")
//    public List<Schedule> getByTeacherId(@PathVariable String teacherId) {
//        return scheduleService.getScheduleByTeacherId(teacherId);
//    }
    @GetMapping("/class/{classId}")
    public Page<Schedule> getByClassId(
            @PathVariable String classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return scheduleService.getScheduleByClassId(classId, page, size);
    }

    @GetMapping("/teacher/{teacherId}")
    public Page<Schedule> getByTeacherId(
            @PathVariable String teacherId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return scheduleService.getScheduleByTeacherId(teacherId, page, size);
    }
}