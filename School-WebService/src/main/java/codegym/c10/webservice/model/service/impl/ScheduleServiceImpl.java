package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.entity.Schedule;
import codegym.c10.webservice.model.repository.IScheduleRepository;
import codegym.c10.webservice.model.service.iface.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    @Autowired
    private IScheduleRepository scheduleRepository;

    @Override
    public Iterable<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Optional<Schedule> findById(Integer id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public void remove(Integer id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> findByTeacherId(Integer teacherId) {
        return scheduleRepository.findByTeacher_Id(teacherId);
    }

    @Override
    public List<Schedule> findByTeacherName(String teacherName) {
        return scheduleRepository.findByTeacherName(teacherName);
    }

    //
}
