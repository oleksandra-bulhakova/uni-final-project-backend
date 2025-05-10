package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {
}
