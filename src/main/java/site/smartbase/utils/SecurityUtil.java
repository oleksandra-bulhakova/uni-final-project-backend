package site.smartbase.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.smartbase.service.AuthService;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final AuthService authService;
    private final HttpServletRequest request;

    public boolean isCurrentUserId(Long userId) {
        if (userId == null) return false;
        String token = extractTokenFromRequest(request);
        Long currentUserId = authService.getCurrentUserId(token);

        return currentUserId.equals(userId);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new IllegalStateException("No JWT token found in request");
    }
}
