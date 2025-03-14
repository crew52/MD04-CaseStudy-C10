package codegym.c10.webservice.model.entity;

import codegym.c10.webservice.model.eNum.GradeLevel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String className;

    @OneToOne
    @JoinColumn(name = "teacher_id", unique = true)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GradeLevel gradeLevel;
}