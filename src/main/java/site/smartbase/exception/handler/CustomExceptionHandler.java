package site.smartbase.exception.handler;

import jakarta.validation.ConstraintDeclarationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import site.smartbase.exception.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorAttributes errorAttributes;

    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex,
                                                                             WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(AzureException.class)
    public final ResponseEntity<Object> handleAzureException(AzureException ex,
                                                                     WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(EmailException.class)
    public final ResponseEntity<Object> handleEmailException(EmailException ex,
                                                                     WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    @ExceptionHandler(NoAccessException.class)
    public final ResponseEntity<Object> handleNoAccessException(NoAccessException ex,
                                                                     WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionResponse);
    }

    @ExceptionHandler(NotActivatedException.class)
    public final ResponseEntity<Object> handleNotActivatedException(NotActivatedException ex,
                                                                     WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex,
                                                                    WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(NotValidTokenException.class)
    public final ResponseEntity<Object> handleNotValidTokenException(NotValidTokenException ex,
                                                                WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(UserAlreadyActivatedException.class)
    public final ResponseEntity<Object> handleUserAlreadyActivatedException(UserAlreadyActivatedException ex,
                                                                     WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public final ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException ex,
                                                                            WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        return new HashMap<>(errorAttributes.getErrorAttributes(webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE)));
    }
}
