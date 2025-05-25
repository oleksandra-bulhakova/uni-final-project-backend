package site.smartbase.annotations;

import java.lang.annotation.*;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;
import site.smartbase.validator.CurrentUserIdValidator;

@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrentUserIdValidator.class)
public @interface CurrentUserIdMatches {
    String message() default "You can only access your own data.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}