package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.UserResponse;
import site.smartbase.entity.User;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepo.findById(userId).
                orElseThrow(() -> new NotFoundException("User not found"));

        return modelMapper.map(user, UserResponse.class);
    }

    @Transactional
    @Override
    public void setUserImage(Long userId, String userImage) {
        User user = userRepo.findById(userId).
                orElseThrow(() -> new NotFoundException("User not found"));
        String cleanPath = userImage.replace("\"", "");
        user.setImagePath(cleanPath);
    }
}
