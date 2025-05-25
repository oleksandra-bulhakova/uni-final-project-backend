package site.smartbase.validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.smartbase.annotations.CurrentUserIdMatches;
import site.smartbase.service.AuthService;

@Component
@RequiredArgsConstructor
public class CurrentUserIdValidator implements ConstraintValidator<CurrentUserIdMatches, Long> {

    private final AuthService authService;
    private final HttpServletRequest request;

    @Override
    public boolean isValid(Long idFromPath, ConstraintValidatorContext constraintValidatorContext) {
        if (idFromPath == null) return false;
        String token = extractTokenFromRequest(request);
        Long currentUserId = authService.getCurrentUserId(token);

        return currentUserId.equals(idFromPath);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new IllegalStateException("No JWT token found in request");
    }
}