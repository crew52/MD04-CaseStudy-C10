package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.entity.Schedule;
import codegym.c10.webservice.model.repository.IScheduleRepository;
import codegym.c10.webservice.model.service.iface.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    @Autowired
    private IScheduleRepository scheduleRepository;

    @Override
    public Iterable<Schedule> findAll() {
        // Implement the method logic here
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule save(Schedule schedule) {
        return null;
    }

    @Override
    public Optional<Schedule> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void remove(Integer id) {

    }
}