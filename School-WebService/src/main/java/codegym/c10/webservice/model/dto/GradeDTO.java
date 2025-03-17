package codegym.c10.webservice.model.dto;

import codegym.c10.webservice.model.eNum.ExamType;
import codegym.c10.webservice.model.eNum.SubjectEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDTO {
    private Integer id;

    private Integer studentId;
    private String studentName;

    private Integer classId;
    private String className;

    private Integer subjectId;
    private SubjectEnum subjectName;

    private Integer teacherId;
    private String teacherName;

    private Float score;
    private ExamType examType;

    private LocalDate date;

}
