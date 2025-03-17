package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IAttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByStudentId(Integer studentId);
    List<Attendance> findByScheduleId(Integer scheduleId);

    @Query("SELECT a FROM Attendance a WHERE " +
            "(:className IS NULL OR LOWER(a.schedule.classEntity.className) LIKE LOWER(CONCAT('%', :className, '%'))) " +
            "AND (:studentName IS NULL OR LOWER(a.student.name) LIKE LOWER(CONCAT('%', :studentName, '%')))")
    Page<Attendance> searchAttendances(@Param("className") String className,
                                       @Param("studentName") String studentName,
                                       Pageable pageable);

    // Teacher: Chỉ lấy danh sách điểm danh của lớp do giáo viên đó dạy
    @Query("SELECT a FROM Attendance a " +
            "JOIN a.schedule sch " +
            "JOIN sch.classEntity c " +
            "JOIN sch.teacher t " +
            "JOIN a.student s " +
            "WHERE t.id = :teacherId " +  // Bắt buộc lọc theo teacherId
            "AND (:className IS NULL OR LOWER(c.className) LIKE LOWER(CONCAT('%', :className, '%'))) " +
            "AND (:studentName IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :studentName, '%')))")

    Page<Attendance> getTeacherAttendances(
            @Param("teacherId") Integer teacherId,
            @Param("className") String className,
            @Param("studentName") String studentName,
            Pageable pageable);

}