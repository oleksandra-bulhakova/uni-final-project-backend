package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Candidate;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {
}
