package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
}
