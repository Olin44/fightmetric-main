package maciej.grochowski.fightmetric.pinnacle.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = TooManyRequestsException.class)
    protected ResponseEntity<Object> handleNotFetchedException(TooManyRequestsException e, WebRequest request) {
        ExceptionMessage body = new ExceptionMessage(
                LocalDateTime.now().toString(), HttpStatus.TOO_MANY_REQUESTS.value(), e.getMessage()
        );
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.TOO_MANY_REQUESTS, request);
    }
}
