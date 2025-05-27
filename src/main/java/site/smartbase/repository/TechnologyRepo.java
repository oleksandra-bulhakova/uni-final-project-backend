package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Technology;

import java.util.List;

@Repository
public interface TechnologyRepo extends JpaRepository<Technology, Long> {
    List<Technology> findByVacancies_Id(Long id);
}
