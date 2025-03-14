package codegym.c10.webservice.model.entity;

import codegym.c10.webservice.model.eNum.ERole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name",unique = true, nullable = false)
    private ERole roleName;
}