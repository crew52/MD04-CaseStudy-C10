package codegym.c10.webservice.service;

import codegym.c10.webservice.model.Schedule;
import codegym.c10.webservice.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

//    public List<Schedule> getScheduleByClassId(String classId) {
//        return scheduleRepository.findByClassId(classId);
//    }
//
//    public List<Schedule> getScheduleByTeacherId(String teacherId) {
//        return scheduleRepository.findByTeacherId(teacherId);
//    }
public Page<Schedule> getScheduleByClassId(String classId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return scheduleRepository.findByClassId(classId, pageable);
}

    public Page<Schedule> getScheduleByTeacherId(String teacherId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findByTeacherId(teacherId, pageable);
    }
}