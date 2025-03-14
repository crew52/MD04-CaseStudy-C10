package codegym.c10.webservice.model.entity;

import codegym.c10.webservice.model.eNum.ExamType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Teacher teacher;

    @Column(nullable = false)
    private Float score;

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false)
    private ExamType examType;

    @Column(nullable = false)
    private LocalDate date;
}