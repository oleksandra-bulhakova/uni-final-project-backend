package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Contact;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c WHERE c.contact =:contact AND c.ownableType =:ownableType AND c.type =:contactType")
    Optional<Contact> findByContactAndOwnableTypeAndContactType(@Param("contact") String email, @Param("ownableType") OwnableType ownableType, @Param("contactType") ContactType contactType);

    @Query("SELECT c FROM Contact c WHERE c.ownerId =:ownerId AND c.ownableType =:ownableType AND c.type =:contactType")
    Optional<Contact> findByOwnerIdAndOwnableTypeAndContactType(@Param("ownerId") Long ownerId, @Param("ownableType") OwnableType ownableType, @Param("contactType") ContactType contactType);

    @Query("SELECT c.ownerId FROM Contact c WHERE c.contact =:contact AND c.ownableType =:ownableType AND c.type =:contactType")
    Optional<Long> findOwnerIdByEmail(@Param("contact") String email, @Param("ownableType") OwnableType ownableType, @Param("contactType") ContactType contactType);

    List<Contact> findByOwnerIdAndOwnableType(Long ownerId, OwnableType ownableType);
}
