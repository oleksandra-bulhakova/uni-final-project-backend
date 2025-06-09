package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.smartbase.dto.VacancyStatusCount;
import site.smartbase.entity.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    @Query("""
                SELECT new site.smartbase.dto.VacancyStatusCount(v.name, a.type, COUNT(a))
                FROM Appointment a
                JOIN Candidate c ON a.participant.id = c.id
                JOIN c.vacancies v
                WHERE v.id = :vacancyId
                GROUP BY v.name, a.type
            """)
    List<VacancyStatusCount> countByVacancyAndTypeForVacancy(@Param("vacancyId") Long vacancyId);

    List<Appointment> findAllByParticipantId(Long id);
}
