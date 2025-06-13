package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserId;
import site.smartbase.dto.UserEditRequest;
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

    @PutMapping("/{userId}")
    @PreAuthorize("@securityUtil.isCurrentUserId(#userId) or hasAuthority('OWNER')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("userId") Long userId, @RequestBody UserEditRequest userEditRequest) {
        return ResponseEntity.ok(userService.updateUser(userId, userEditRequest));
    }

    @PutMapping("status/{userId}")
    public ResponseEntity<Void> setStatus(@PathVariable("userId") Long userId, @RequestParam Boolean status) {
        userService.setStatus(userId, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<UserResponse>> getByActive(@RequestParam Boolean active, @CurrentUserId Long currentUserId) {
        return ResponseEntity.ok(userService.getUsersByActive(currentUserId, active));
    }
}
