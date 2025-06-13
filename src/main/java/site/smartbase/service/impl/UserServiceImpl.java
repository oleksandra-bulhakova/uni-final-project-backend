package site.smartbase.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.smartbase.dto.UserEditRequest;
import site.smartbase.dto.UserResponse;
import site.smartbase.entity.Company;
import site.smartbase.entity.User;
import site.smartbase.exception.NotFoundException;
import site.smartbase.repository.CompanyRepo;
import site.smartbase.repository.UserRepo;
import site.smartbase.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final CompanyRepo companyRepo;

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

    @Override
    public List<UserResponse> getAllUsers(Long currentUserId) {
        Company company = companyRepo.findById(userRepo.findById(currentUserId)
                        .orElseThrow(() -> new NotFoundException("User not found")).getCompany().getId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        List<User> users = userRepo.findByCompany_id(company.getId());

        List<UserResponse> userResponses = null;
        if (users != null) {
            userResponses = users.stream()
                    .map(user -> modelMapper.map(user, UserResponse.class)).toList();
        }
        return userResponses;
    }

    @Transactional
    @Override
    public UserResponse updateUser(Long userId, UserEditRequest userEditRequest) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        user.setFirstName(userEditRequest.getFirstName());
        user.setLastName(userEditRequest.getLastName());

        return modelMapper.map(userRepo.save(user), UserResponse.class);
    }

    @Transactional
    @Override
    public void setStatus(Long userId, Boolean status) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        user.setActive(status);
    }

    @Override
    public List<UserResponse> getUsersByActive(Long currentUserId, boolean active) {
        User currentUser = userRepo.findById(currentUserId).orElseThrow(() -> new NotFoundException("User not found"));
        companyRepo.findById(currentUser.getCompany().getId()).orElseThrow(() -> new NotFoundException("Company not found"));
        List<User> users = userRepo.findAllByActiveAndCompany_id(active, currentUser.getCompany().getId());
        return users.stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
    }
}
