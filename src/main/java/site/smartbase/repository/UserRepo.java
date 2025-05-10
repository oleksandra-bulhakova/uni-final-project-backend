package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
