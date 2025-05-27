package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Address;
import site.smartbase.enums.OwnableType;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    List<Address> findByOwnerIdAndOwnableType(Long ownerId, OwnableType ownableType);
}
