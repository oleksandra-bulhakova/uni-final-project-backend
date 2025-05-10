package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Vacancy;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
}
