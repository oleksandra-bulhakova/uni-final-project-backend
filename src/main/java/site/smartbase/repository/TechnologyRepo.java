package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Technology;

@Repository
public interface TechnologyRepo extends JpaRepository<Technology, Long> {
}
