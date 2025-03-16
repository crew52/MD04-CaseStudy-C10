package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.dto.AttendanceDTO;
import codegym.c10.webservice.model.entity.Attendance;
import codegym.c10.webservice.model.entity.Schedule;
import codegym.c10.webservice.model.entity.Student;
import codegym.c10.webservice.model.repository.IAttendanceRepository;
import codegym.c10.webservice.model.repository.IScheduleRepository;
import codegym.c10.webservice.model.repository.IStudentRepository;
import codegym.c10.webservice.model.service.iface.IAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements IAttendanceService {

    @Autowired
    private  IAttendanceRepository iAttendanceRepository;

    @Autowired
    private  IStudentRepository studentRepository;

    @Autowired
    private  IScheduleRepository scheduleRepository;

    @Override
    public Iterable<AttendanceDTO> findAll() {
        return iAttendanceRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<AttendanceDTO> findById(Integer id) {
        return iAttendanceRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public AttendanceDTO save(AttendanceDTO attendanceDTO) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void remove(Integer id) {
        iAttendanceRepository.deleteById(id);
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        return AttendanceDTO.builder()
                .id(attendance.getId())
                .studentId(attendance.getStudent().getId())
                .scheduleId(attendance.getSchedule().getId())
                .studentName(attendance.getStudent().getName()) // Lấy tên học sinh
                .status(attendance.getStatus())
                .className(attendance.getSchedule().getClassEntity().getClassName()) // Lấy tên lớp
                .subjectName(attendance.getSchedule().getSubject().getSubjectName().toString())
                .teacherName(attendance.getSchedule().getTeacher().getName()) // Lấy tên giáo viên
                .date(attendance.getSchedule().getDate()) // Lấy ngày học
                .startTime(attendance.getSchedule().getStartTime()) // Lấy giờ bắt đầu
                .endTime(attendance.getSchedule().getEndTime()) // Lấy giờ kết thúc
                .build();
    }

    private Attendance convertToEntity(AttendanceDTO attendanceDTO) {
        Student student = studentRepository.findById(attendanceDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Schedule schedule = scheduleRepository.findById(attendanceDTO.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        return new Attendance(attendanceDTO.getId(), student, schedule, attendanceDTO.getStatus());
    }
}