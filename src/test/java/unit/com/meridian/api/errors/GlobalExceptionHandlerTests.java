package unit.com.meridian.api.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.meridian.api.errors.ErrorResponse;
import com.meridian.api.errors.GlobalExceptionHandler;
import com.meridian.api.errors.ResourceNotFoundException;
import com.meridian.security.JwtAuthenticationException;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class GlobalExceptionHandlerTests {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void globalExceptionHandler_handleResourceNotFoundException() {

        ResourceNotFoundException resourceNotFoundException =
                new ResourceNotFoundException("Testing message");

        ResponseEntity<ErrorResponse> responseEntity =
                globalExceptionHandler.handleResourceNotFoundException(resourceNotFoundException);

        assertEquals("Not Found", responseEntity.getBody().getError());
        assertEquals("Testing message", responseEntity.getBody().getMessage());
        assertEquals(404, responseEntity.getBody().getStatus());
    }

    @Test
    void globalExceptionHandler_handleJwtAuthenticationException() {

        JwtAuthenticationException jwtException =
                new JwtAuthenticationException("Authentication failed");

        ResponseEntity<ErrorResponse> responseEntity =
                globalExceptionHandler.handleJwtAuthenticationException(jwtException);

        assertEquals("Unauthorized", responseEntity.getBody().getError());
        assertEquals("Authentication failed", responseEntity.getBody().getMessage());
        assertEquals(401, responseEntity.getBody().getStatus());
    }

    @Test
    void globalExceptionHandler_handleAuthenticationException() {

        AuthenticationException authException = mock(AuthenticationException.class);
        when(authException.getMessage()).thenReturn("Authentication failed");

        ResponseEntity<ErrorResponse> responseEntity =
                globalExceptionHandler.handleAuthenticationException(authException);

        assertEquals("Unauthorized", responseEntity.getBody().getError());
        assertEquals("Authentication failed", responseEntity.getBody().getMessage());
        assertEquals(401, responseEntity.getBody().getStatus());
    }

    @Test
    void globalExceptionHandler_handleAccessDeniedException() {

        AccessDeniedException accessDeniedException =
                new AccessDeniedException("Access is denied");

        ResponseEntity<ErrorResponse> responseEntity =
                globalExceptionHandler.handleAccessDeniedException(accessDeniedException);

        assertEquals("Forbidden", responseEntity.getBody().getError());
        assertEquals("Access is denied", responseEntity.getBody().getMessage());
        assertEquals(403, responseEntity.getBody().getStatus());
    }

    @Test
    void globalExceptionHandler_handleGlobalException() {

        Exception exception = new Exception("Testing Message");

        ResponseEntity<ErrorResponse> responseEntity =
                globalExceptionHandler.handleGlobalException(exception);

        assertEquals("Internal Server Error", responseEntity.getBody().getError());
        assertEquals("Testing Message", responseEntity.getBody().getMessage());
        assertEquals(500, responseEntity.getBody().getStatus());
    }

    @Test
    void globalExceptionHandler_handleValidationException() {

        MethodParameter methodParameter = mock(MethodParameter.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("objectName", "error", "Validation failed");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Object> responseEntity =
                globalExceptionHandler.handleValidationExceptions(exception);

        assertEquals("{error=Validation failed}", responseEntity.getBody().toString());
        assertEquals(400, responseEntity.getStatusCode().value());
    }
}
