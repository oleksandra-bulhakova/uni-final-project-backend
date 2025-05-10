package site.smartbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.smartbase.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
