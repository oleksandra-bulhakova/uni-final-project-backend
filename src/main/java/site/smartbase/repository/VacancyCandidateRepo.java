package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.smartbase.dto.GeneralStatistic;
import site.smartbase.entity.VacancyCandidate;
import site.smartbase.entity.embeddedId.VacancyCandidateId;

import java.time.LocalDateTime;
import java.util.List;

public interface VacancyCandidateRepo extends JpaRepository<VacancyCandidate, VacancyCandidateId> {
    @Query("""
                SELECT new site.smartbase.dto.GeneralStatistic(vc.addedBy.id, vc.addedBy.firstName, vc.addedBy.lastName, vc.addedBy.imagePath, COUNT(vc))
                FROM VacancyCandidate vc
                WHERE vc.addedBy.id IN :userIds
                  AND vc.dateAdded BETWEEN :start AND :end
                GROUP BY vc.addedBy.id, vc.addedBy.firstName, vc.addedBy.lastName, vc.addedBy.imagePath
            """)
    List<GeneralStatistic> countByUsersBetweenDates(
            @Param("userIds") List<Long> userIds,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
