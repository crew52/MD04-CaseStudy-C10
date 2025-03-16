package codegym.c10.webservice.model.dto;

import codegym.c10.webservice.model.eNum.AttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Integer id;
    private Integer studentId;
    private Integer scheduleId;
    private String studentName; // Thêm tên học sinh
    private AttendanceStatus status;
    private String className;  // Tên lớp
    private String subjectName; // Tên môn học
    private String teacherName; // Tên giáo viên
    private LocalDate date; // Ngày học
    private LocalTime startTime; // Thời gian bắt đầu
    private LocalTime endTime; // Thời gian kết thúc

}


