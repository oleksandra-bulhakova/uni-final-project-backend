package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByUsers_Id(Long userId);

    List<Vacancy> findByClient_Id(Long companyId);

    List<Vacancy> findAllByCompany_Id(Long companyId);

    @Query("SELECT vc.vacancy FROM VacancyCandidate vc WHERE vc.candidate.id = :candidateId")
    List<Vacancy> findAllByCandidateId(@Param("candidateId") Long candidateId);
}
