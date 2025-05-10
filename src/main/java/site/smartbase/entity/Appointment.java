package site.smartbase.entity;

import jakarta.persistence.*;
import lombok.*;
import site.smartbase.enums.AppointmentStatus;
import site.smartbase.enums.AppointmentType;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AppointmentType type;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User host;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate participant;

    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;
}
