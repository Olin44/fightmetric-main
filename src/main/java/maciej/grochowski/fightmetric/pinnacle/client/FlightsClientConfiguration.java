package maciej.grochowski.fightmetric.pinnacle.client;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightsClientConfiguration {

    @Bean
    ErrorDecoder errorDecoder() {
        return new FlightsClientErrorDecoder();
    }
}
