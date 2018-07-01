package de.malkusch.getroom.infrastructure;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestConfiguration {

    @Bean
    RestTemplate restTemplate(@Value("${timeout}") String timeout, RestTemplateBuilder builder) {
        Duration timeoutDuration = Duration.parse(timeout);
        return builder.setConnectTimeout((int) timeoutDuration.toMillis()).additionalInterceptors(defaultUserAgent())
                .setReadTimeout((int) timeoutDuration.toMillis()).build();
    }

    @Value("${userAgent}")
    private String userAgent;

    private ClientHttpRequestInterceptor defaultUserAgent() {
        return (request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.USER_AGENT, userAgent);
            return execution.execute(request, body);
        };
    }

}
