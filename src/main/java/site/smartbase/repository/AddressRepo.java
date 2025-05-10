package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
}
