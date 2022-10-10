package sk.ness.academy.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sk.ness.academy.exception.BlogException;

import java.util.Map;

@ControllerAdvice
public class BlogHandler {

    @ExceptionHandler(BlogException.class)
    public ResponseEntity<?> handleException(BlogException e) {
        return new ResponseEntity<>(
                Map.of("error", e.getMessage()), e.getStatus());
    }

}
