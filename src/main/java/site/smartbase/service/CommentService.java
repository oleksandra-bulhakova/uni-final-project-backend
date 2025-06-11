package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.CommentRequest;
import site.smartbase.dto.CommentResponse;

public interface CommentService {
    @Transactional
    CommentResponse updateComment(Long commentId, CommentRequest commentRequest);

    @Transactional
    void deleteComment(Long commentId);
}
