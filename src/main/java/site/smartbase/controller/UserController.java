package site.smartbase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.smartbase.annotations.CurrentUserIdMatches;
import site.smartbase.dto.UserResponse;
import site.smartbase.service.UserService;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") @CurrentUserIdMatches Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/set-image-path/{userId}")
    public ResponseEntity<Void> setImagePath(@RequestBody String imagePath,
                                             @PathVariable("userId") Long userId) {
        userService.setUserImage(userId, imagePath);
        return ResponseEntity.noContent().build();
    }
}
