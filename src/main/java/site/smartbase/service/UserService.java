package site.smartbase.service;

import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.UserResponse;

public interface UserService {
    UserResponse getUser(Long userId);

    @Transactional
    void setUserImage(Long userId, String userImage);
}
