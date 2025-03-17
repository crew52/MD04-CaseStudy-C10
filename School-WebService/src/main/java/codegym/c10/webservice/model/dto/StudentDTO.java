package codegym.c10.webservice.model.dto;

import codegym.c10.webservice.model.eNum.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StudentDTO {
        private Integer id;
        private String name;
        private LocalDate dob;
        private Gender gender;
        private Integer classId;
        private String className;
        private String parentContact;


}


