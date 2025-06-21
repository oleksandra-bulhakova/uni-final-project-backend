package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByToken(String token);

    List<User> findByCompany_id(Long companyId);

    List<User> findAllByActiveAndCompany_id(boolean active, Long companyId);

    @Query("""
                SELECT DISTINCT a.host.id
                FROM Appointment a
                WHERE a.host.id IN :userIds
                  AND a.date BETWEEN :start AND :end
            """)
    List<Long> findUserIdsWithAppointmentsBetweenDates(
            @Param("userIds") List<Long> userIds,
            @Param("start") OffsetDateTime start,
            @Param("end") OffsetDateTime end
    );
}
