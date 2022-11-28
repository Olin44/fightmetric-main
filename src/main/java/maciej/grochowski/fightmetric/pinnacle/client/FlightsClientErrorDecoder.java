package maciej.grochowski.fightmetric.pinnacle.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import maciej.grochowski.fightmetric.pinnacle.exception.TooManyRequestsException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FlightsClientErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.TOO_MANY_REQUESTS.value()) {
            return new TooManyRequestsException();
        } else {
            return errorDecoder.decode(methodKey, response);
        }
    }
}
