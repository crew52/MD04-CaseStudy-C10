package codegym.c10.webservice.model.dto;

import codegym.c10.webservice.model.entity.Teacher;
import codegym.c10.webservice.model.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherUserRequestDTO {
    private Teacher teacher;
    private User user;
}
