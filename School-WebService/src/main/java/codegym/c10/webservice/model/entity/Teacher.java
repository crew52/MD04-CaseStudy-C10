package codegym.c10.webservice.model.entity;

import codegym.c10.webservice.model.eNum.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher extends Person {

    @Column(unique = true, length = 20)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;
}