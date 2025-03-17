package codegym.c10.webservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classId;
    private String teacherId;
    private String subject;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}