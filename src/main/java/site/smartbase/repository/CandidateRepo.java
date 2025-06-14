package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Candidate;
import site.smartbase.entity.Technology;

import java.util.List;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    List<Candidate> findByCompany_Id(Long companyId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM candidates c " +
            "WHERE c.company_id =:companyId " +
            "AND LOWER(CONCAT (c.first_name, ' ', c.last_name)) LIKE LOWER(CONCAT ('%', :name, '%'))")
    List<Candidate> searchCandidateByName(@Param("name") String name, @Param("companyId") Long companyId);

    @Query(nativeQuery = true,
    value = "SELECT c.* FROM candidates c " +
            "JOIN candidate_technology ct ON c.id = ct.candidate_id " +
            "WHERE c.company_id =:companyId " +
            "AND ct.technology_id IN (:technologies) " +
            "GROUP BY c.id " +
            "HAVING COUNT(DISTINCT ct.technology_id) =:techCount")
    List<Candidate> searchCandidateByTechnologies(@Param("companyId") Long companyId, @Param("technologies") List<Long> technologiesIds,
                                                  @Param("techCount") Long techCount);


}
