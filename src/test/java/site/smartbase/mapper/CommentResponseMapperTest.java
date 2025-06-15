package site.smartbase.mapper;

import org.junit.jupiter.api.Test;
import site.smartbase.ModelUtils;
import site.smartbase.dto.CandidateResponse;
import site.smartbase.dto.CommentResponse;
import site.smartbase.dto.UserResponse;
import site.smartbase.entity.Comment;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentResponseMapperTest {
    private final CommentResponseMapper mapper = new CommentResponseMapper();

    @Test
    void convertTest() {
        Comment comment = ModelUtils.getComment();

        CommentResponse response = mapper.convert(comment);

        assertNotNull(response);
        assertEquals(response.getId(), 10L);
        assertEquals(response.getDescription(),"Great candidate!");
        assertEquals(response.getDate(), LocalDate.of(2024, 6, 1));

        UserResponse authorResp = response.getAuthor();
        assertNotNull(authorResp);
        assertEquals(authorResp.getId(), 1L);
        assertEquals(authorResp.getFirstName(), "John");
        assertEquals(authorResp.getLastName(), "Doe");
        assertEquals(authorResp.getImagePath(), "/avatars/john.png");

        CandidateResponse addresseeResp = response.getAddressee();
        assertNotNull(addresseeResp);
        assertEquals(addresseeResp.getId(), 2L);
        assertEquals(addresseeResp.getFirstName(), "Jane");
        assertEquals(addresseeResp.getLastName(), "Smith");
    }
}
