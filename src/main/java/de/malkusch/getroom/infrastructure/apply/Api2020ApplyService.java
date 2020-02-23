package de.malkusch.getroom.infrastructure.apply;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.malkusch.getroom.infrastructure.AuthenticationService;
import de.malkusch.getroom.infrastructure.UserIdService;
import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.apply.ApplyService;
import de.malkusch.getroom.model.apply.Letter;

@Service
public class Api2020ApplyService implements ApplyService {

    Api2020ApplyService(@Value("${apply.start}") String start, @Value("${apply.submit}") String submit,
            UserIdService userIdService, AuthenticationService authentication, RestTemplate rest) {

        this.start = start;
        this.submit = submit;
        this.userId = userIdService.userId;
        this.authentication = authentication;
        this.rest = rest;
    }

    private final String start;
    private final String submit;
    private final String userId;
    private final AuthenticationService authentication;
    private final RestTemplate rest;

    @Override
    public void apply(Room room, Letter letter) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.TEXT_HTML);
        headers.set("Accept", "text/plain, application/json, application/*+json, */*");
        headers.set(HttpHeaders.COOKIE, authentication.getAuthenticatedCookies().toString());

        HttpEntity<String> startRequest = new HttpEntity<>(headers);

        ResponseEntity<String> startResponse = rest.exchange(start, HttpMethod.GET, startRequest, String.class,
                room.id().toString());
        String csrf = Jsoup.parse(startResponse.getBody()).select("input.csrf_token").val();

        SendMessageDto payload = new SendMessageDto();
        payload.user_id = userId;
        payload.csrf_token = csrf;
        payload.messages = new SendMessageDto.Message[] { new SendMessageDto.Message() };
        payload.messages[0].content = letter.message();
        payload.ad_id = room.id().toString();

        HttpEntity<SendMessageDto> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = rest.exchange(submit, HttpMethod.POST, request, String.class);
        if (response.getStatusCodeValue() != 200) {
            throw new IOException("Failed to apply:\n" + response.getBody());
        }
    }

    private static class SendMessageDto {

        public String user_id;
        public String csrf_token;
        public Message[] messages;

        private static class Message {
            public String content;
            public final String message_type = "text";
        }

        public final String ad_type = "0";
        public String ad_id;
    }
}
