package codegym.c10.webservice.model.entity;

import codegym.c10.webservice.model.eNum.SubjectEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_name", nullable = false)
    private SubjectEnum subjectName;
}