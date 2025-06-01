package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Candidate;

import java.util.List;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    List<Candidate> findByCompany_Id(Long companyId);
}
