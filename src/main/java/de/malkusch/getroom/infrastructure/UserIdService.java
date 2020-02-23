package de.malkusch.getroom.infrastructure;

import java.net.URI;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public final class UserIdService {

    UserIdService(@Value("${profileUrl}") String profileUrl, AuthenticationService auth, RestTemplate rest) {
        this.auth = auth;
        this.profileUrl = profileUrl;
        this.rest = rest;
        userId = detectUserId();
    }

    private final AuthenticationService auth;
    private final String profileUrl;
    private final RestTemplate rest;
    public final String userId;

    private String detectUserId() {
        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.TEXT_HTML);
        headers.set("Accept", "text/plain, application/json, application/*+json, */*");
        headers.set(HttpHeaders.COOKIE, auth.getAuthenticatedCookies().toString());
        RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(profileUrl));

        ResponseEntity<String> profile = rest.exchange(profileUrl, HttpMethod.GET, request, String.class);

        String parsed = Jsoup.parse(profile.getBody()).select("*[data-user_id]").first().attr("data-user_id");
        return parsed;
    }
}
