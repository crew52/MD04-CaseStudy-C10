// IScheduleService.java
package codegym.c10.webservice.model.service.iface;

import codegym.c10.webservice.model.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface IScheduleService {
    Iterable<Schedule> findAll();
    Schedule save(Schedule schedule);
    Optional<Schedule> findById(Integer id);
    void remove(Integer id);

    // tìm kiem theo id cua teacher
    List<Schedule> findByTeacherId(Integer teacherId);

    //tim kiem theo ten
    List<Schedule> findByTeacherName(String teacherName);
}