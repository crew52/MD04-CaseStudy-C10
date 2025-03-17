package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.dto.AttendanceDTO;
import codegym.c10.webservice.model.dto.GradeDTO;
import codegym.c10.webservice.model.entity.*;
import codegym.c10.webservice.model.repository.*;
import codegym.c10.webservice.model.service.iface.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements IGradeService {

    @Autowired
    private IGradeRepository iGradeRepository;

    @Autowired
    private IStudentRepository iStudentRepository;

    @Autowired
    private ISubjectRepository iSubjectRepository;

    @Autowired
    private ITeacherRepository iTeacherRepository;

    @Override
    public Iterable<GradeDTO> findAll() {
        return iGradeRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public GradeDTO save(GradeDTO gradeDTO) {
        Grade grade = convertToEntity(gradeDTO); // Chuyển DTO thành Entity
        Grade savedGrade = iGradeRepository.save(grade); // Lưu vào DB
        return convertToDTO(savedGrade); // Chuyển lại thành DTO để trả về
    }

    @Override
    public Optional<GradeDTO> findById(Integer id) {
        return iGradeRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public void remove(Integer id) {
        iGradeRepository.deleteById(id);
    }

    private GradeDTO convertToDTO(Grade grade) {
        return GradeDTO.builder()
                .id(grade.getId())
                .studentId(grade.getStudent().getId())
                .studentName(grade.getStudent().getName()) // Lấy tên học sinh
                .classId(grade.getStudent().getClassEntity().getId())
                .className(grade.getStudent().getClassEntity().getClassName()) // Lấy tên lớp
                .subjectId(grade.getSubject().getId()) // Lấy tên lớp
                .subjectName(grade.getSubject().getSubjectName())
                .teacherId(grade.getTeacher().getId()) // Lấy tên giáo viên
                .teacherName(grade.getTeacher().getName()) // Lấy tên giáo viên
                .score(grade.getScore()) // Lấy ngày học
                .examType(grade.getExamType()) // Lấy giờ bắt đầu
                .date(grade.getDate()) // Lấy giờ kết thúc
                .build();
    }

    private Grade convertToEntity(GradeDTO gradeDTO) {
        Student student = iStudentRepository.findById(gradeDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = iSubjectRepository.findById(gradeDTO.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        Teacher teacher = iTeacherRepository.findById(gradeDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        return new Grade(gradeDTO.getId(), student, subject, teacher, gradeDTO.getScore(), gradeDTO.getExamType(), gradeDTO.getDate());
    }
}
