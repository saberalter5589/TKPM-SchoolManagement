package school.management.school_management_be.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            NoDataFoundException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessageInfo().getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(DataDuplicatedException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            DataDuplicatedException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessageInfo().getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            UnAuthorizationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessageInfo().getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            BadRequestException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessageInfo().getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }
}
