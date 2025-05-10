package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Attachment;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
}
