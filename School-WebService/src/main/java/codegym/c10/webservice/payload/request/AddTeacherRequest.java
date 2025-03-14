package codegym.c10.webservice.payload.request;

import codegym.c10.webservice.model.eNum.Gender;
import codegym.c10.webservice.model.entity.Subject;
import codegym.c10.webservice.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class AddTeacherRequest {
    @NotBlank(message = "Tên giáo viên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(min = 10, max = 10, message = "Số điện thoại phải có 10 số")
    private String phone;

    @NotBlank(message = "ngày sinh không được để trống")
    private LocalDate dob;

    @NotBlank(message = "Giới tính không được để trống")
    private Gender gender;

    @NotBlank(message = "Không được để trống bộ môn giảng dạy")
    private Subject subject;

    private User user;
}
