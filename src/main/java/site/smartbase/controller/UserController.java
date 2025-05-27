package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.UserResponse;
import site.smartbase.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/set-image-path/{userId}")
    public ResponseEntity<Void> setImagePath(@RequestBody String imagePath,
                                             @PathVariable("userId") Long userId) {
        userService.setUserImage(userId, imagePath);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@CurrentUserId Long currentUserId) {
        return ResponseEntity.ok(userService.getAllUsers(currentUserId));
    }
}
