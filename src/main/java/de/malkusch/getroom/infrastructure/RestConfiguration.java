package de.malkusch.getroom.infrastructure;

import java.time.Duration;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestConfiguration {

    @Value("${userAgent}")
    private String userAgent;

    @Bean
    RestTemplate restTemplate(HttpClient client, RestTemplateBuilder builder) {
        return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client)).build();
    }

    @Bean
    CloseableHttpClient httpClient(@Value("${timeout}") String timeout) {
        Duration timeoutDuration = Duration.parse(timeout);
        int timeoutMillis = (int) timeoutDuration.toMillis();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(10);
        cm.setDefaultMaxPerRoute(10);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeoutMillis)
                .setConnectionRequestTimeout(timeoutMillis).setSocketTimeout(timeoutMillis).build();
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).disableCookieManagement()
                .setUserAgent(userAgent).setDefaultRequestConfig(requestConfig).build();
        return httpclient;
    }
}
