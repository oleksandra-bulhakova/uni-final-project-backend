package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.smartbase.repository.CommentRepo;
import site.smartbase.service.CommentService;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;

}
