package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.eNum.DayOfWeekEnum;
import codegym.c10.webservice.model.entity.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IScheduleRepository extends CrudRepository<Schedule, Integer> {

    // Tìm lịch học theo class_id
    List<Schedule> findByClassEntity_Id(Integer classId);

    // Tìm lịch học theo teacher_id
    List<Schedule> findByTeacher_Id(Integer teacherId);

    // Tìm lịch học theo subject_id
    List<Schedule> findBySubject_Id(Integer subjectId);

    // Dùng @Query để viết truy vấn SQL tùy chỉnh
    @Query("SELECT s FROM Schedule s WHERE s.classEntity.id = :classId AND s.dayOfWeek = :dayOfWeek")
    List<Schedule> findByClassAndDay(@Param("classId") Integer classId, @Param("dayOfWeek") DayOfWeekEnum dayOfWeek);
}
