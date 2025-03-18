package codegym.c10.webservice.model.dto;

import codegym.c10.webservice.model.eNum.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherProfileDTO {
    private Integer teacherId;
    private String name;
    private LocalDate dob;
    private String gender;
    private String phone;
    private String email;
    private SubjectEnum subjectName;
    private Integer userId;
    private String username;
    private ClassesDTO classInfo;
    private List<StudentDTO> students;


}
