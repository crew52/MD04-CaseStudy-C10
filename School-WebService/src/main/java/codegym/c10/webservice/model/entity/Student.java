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
@Table(name = "students")
public class Student extends Person {

    @Column(columnDefinition = "TEXT")
    private String address;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Classes classEntity;

    @Column(length = 50)
    private String parentContact;
}