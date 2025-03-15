package codegym.c10.webservice.payload.request;

import codegym.c10.webservice.model.eNum.ExamType;
import codegym.c10.webservice.model.eNum.GradeLevel;
import codegym.c10.webservice.model.entity.Student;
import codegym.c10.webservice.model.entity.Subject;
import codegym.c10.webservice.model.entity.Teacher;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AddGradeRequest {
    @NotBlank(message = "Tên lóp học không được để trống")
    private String name;

    private GradeLevel gradeLevel;

    private Student student;

    private Teacher teacher;

    private Subject subject;

    private Float score;

    private ExamType examType;

    private LocalDate date;

}
