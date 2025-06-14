package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.smartbase.dto.VacancyStatusCount;
import site.smartbase.entity.Appointment;
import site.smartbase.enums.AppointmentType;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    @Query("""
                SELECT new site.smartbase.dto.VacancyStatusCount(a.vacancy.name, a.type, COUNT(a))
                FROM Appointment a
                WHERE a.vacancy.id = :vacancyId
                GROUP BY a.vacancy.name, a.type
            """)
    List<VacancyStatusCount> countByVacancyAndTypeForVacancy(@Param("vacancyId") Long vacancyId);

    List<Appointment> findAllByParticipantId(Long id);

    @Query("""
            SELECT COUNT(*) FROM Appointment a WHERE a.host.id = :hostId AND a.type =:type AND a.date BETWEEN :start AND :end
            """)
    Long countByTypeAndDateForHost(@Param("hostId") Long hostId,
                                   @Param("start") OffsetDateTime start,
                                   @Param("end") OffsetDateTime end,
                                   @Param("type") AppointmentType type);
}
