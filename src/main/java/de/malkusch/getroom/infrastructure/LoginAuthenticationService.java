package de.malkusch.getroom.infrastructure;

import java.io.IOException;
import java.net.HttpCookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
final class LoginAuthenticationService implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationService.class);

    LoginAuthenticationService(@Value("${login.username}") String username, @Value("${login.password}") String password,
            @Value("${login.url}") String api, @Value("${homeUrl}") String home, RestTemplate rest) throws IOException {

        this.username = username;
        this.password = password;
        this.api = api;
        this.home = home;
        this.rest = rest;

        authenticate();
    }

    private volatile AuthenticationCookies cookies;

    @Override
    public AuthenticationCookies getAuthenticatedCookies() {
        return cookies;
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void reauthenticate() throws IOException {
        authenticate();
    }

    private final String api;
    private final String home;
    private final String username;
    private final String password;
    private final RestTemplate rest;

    @SuppressWarnings("unused")
    private static final class Login {
        private String login_email_username;
        private String login_password;
    }

    private void authenticate() throws IOException {
        Login login = new Login();
        login.login_email_username = username;
        login.login_password = password;

        LOGGER.debug("Login {}", username);
        try {
            ResponseEntity<?> response = rest.postForEntity(api, new HttpEntity<>(login), null);
            cookies = new AuthenticationCookies(HttpCookie.parse(response.getHeaders().getFirst("Set-Cookie")));

            // For some reasone this is necessary after a login
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.COOKIE, cookies.toString());
            rest.exchange(home, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        } catch (RestClientException e) {
            throw new IOException(String.format("Could not login %s", username), e);
        }
    }

}
