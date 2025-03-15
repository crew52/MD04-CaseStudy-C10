package codegym.c10.webservice.payload.request;

import codegym.c10.webservice.model.eNum.Gender;
import codegym.c10.webservice.model.entity.Classes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AddStudentRequest {
    @NotBlank(message = "Tên không được để trống")
private String name;

    @NotBlank(message = "Ngày sinh không được để trống")
private LocalDate dob;

    @NotBlank(message = "Giới tính không được để trống")
private Gender gender;

    @NotBlank(message = "Địa chỉ không được để trống")
private String address;

    @NotBlank(message = "Số điện thoại phụ huynh không được để trống")
    @Size(min = 10, max = 10, message = "Số điện thoại phụ huynh phải có 10 số")
private String parentContact;

}

