package codegym.c10.webservice.repository;


import codegym.c10.webservice.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
//    List<Schedule> findByClassId(String classId);
//    List<Schedule> findByTeacherId(String teacherId);


    Page<Schedule> findByClassId(String classId, Pageable pageable);
    Page<Schedule> findByTeacherId(String teacherId, Pageable pageable);
}