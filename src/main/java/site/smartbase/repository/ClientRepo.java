package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Client;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    List<Client> findAllByCompany_Id(Long companyId);
}
