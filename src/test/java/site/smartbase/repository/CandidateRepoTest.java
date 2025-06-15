package site.smartbase.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import site.smartbase.ModelUtils;
import site.smartbase.entity.Candidate;
import site.smartbase.entity.Company;
import site.smartbase.entity.Technology;
import site.smartbase.repository.util.PostgresInitializer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CandidateRepoTest extends PostgresInitializer {

    @Autowired
    private CandidateRepo candidateRepo;
    @Autowired
    private EntityManager entityManager;

    @AfterEach
    void teardown() {
        entityManager.createNativeQuery("TRUNCATE TABLE candidates RESTART IDENTITY CASCADE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE technologies RESTART IDENTITY CASCADE").executeUpdate();
    }

    @Test
    void searchCandidateByTechnologiesTest() {
        List<Technology> technologies = ModelUtils.getListOfTechnologies();
        technologies.forEach(entityManager::persist);

        List<Company> companies = ModelUtils.getCompanies();
        companies.forEach(entityManager::persist);
        Company companyA = companies.get(0);
        Company companyB = companies.get(1);

        List<Candidate> candidates = ModelUtils.getListOfCandidatesWithTechnologies(technologies);

        candidates.get(0).setCompany(companyA);
        candidates.get(1).setCompany(companyB);
        candidates.get(2).setCompany(companyB);

        candidates.forEach(entityManager::persist);

        entityManager.flush();
        entityManager.clear();

        List<Long> techIds = List.of(
                technologies.get(0).getId(),
                technologies.get(1).getId()
        );
        long techCount = techIds.size();

        List<Candidate> result = candidateRepo.searchCandidateByTechnologies(companyA.getId(), techIds, techCount);
        List<String> names = result.stream().map(Candidate::getFirstName).toList();

        assertTrue(names.contains("John"));
        assertFalse(names.contains("Jane"));
        assertFalse(names.contains("Jack"));
    }
}
