package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
}
