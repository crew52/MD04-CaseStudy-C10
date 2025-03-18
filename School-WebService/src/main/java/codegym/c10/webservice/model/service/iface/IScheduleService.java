// IScheduleService.java
package codegym.c10.webservice.model.service.iface;

import codegym.c10.webservice.model.entity.Schedule;

import java.util.Optional;

public interface IScheduleService {
    Iterable<Schedule> findAll();
    Schedule save(Schedule schedule);
    Optional<Schedule> findById(Integer id);
    void remove(Integer id);
}