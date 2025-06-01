package site.smartbase.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import site.smartbase.dto.CandidateResponse;
import site.smartbase.dto.CommentResponse;
import site.smartbase.dto.UserResponse;
import site.smartbase.entity.Comment;

@Component
@RequiredArgsConstructor
public class CommentResponseMapper extends AbstractConverter<Comment, CommentResponse> {
    @Override
    protected CommentResponse convert(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .date(comment.getDate())
                .description(comment.getDescription())
                .author(UserResponse.builder()
                        .id(comment.getAuthor().getId())
                        .firstName(comment.getAuthor().getFirstName())
                        .lastName(comment.getAuthor().getLastName())
                        .imagePath(comment.getAuthor().getImagePath())
                        .build())
                .addressee(CandidateResponse.builder()
                        .id(comment.getAddressee().getId())
                        .firstName(comment.getAddressee().getFirstName())
                        .lastName(comment.getAddressee().getLastName())
                        .build())
                .build();
    }
}
