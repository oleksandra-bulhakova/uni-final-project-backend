package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.CommentRequest;
import site.smartbase.dto.CommentResponse;
import site.smartbase.entity.Comment;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.CommentRepo;
import site.smartbase.service.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found"));

        comment.setDescription(commentRequest.getDescription());
        return modelMapper.map(comment, CommentResponse.class);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}
