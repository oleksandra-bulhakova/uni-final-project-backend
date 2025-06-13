package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByToken(String token);

    List<User> findByCompany_id(Long companyId);

    List<User> findAllByActiveAndCompany_id(boolean active, Long companyId);
}
