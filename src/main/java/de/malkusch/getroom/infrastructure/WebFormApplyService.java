package de.malkusch.getroom.infrastructure;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.RoomId;
import de.malkusch.getroom.model.apply.ApplyService;
import de.malkusch.getroom.model.apply.Gender;
import de.malkusch.getroom.model.apply.Letter;

@Service
class WebFormApplyService implements ApplyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebFormApplyService.class);

    WebFormApplyService(@Value("${applyUrl}") String applyUrl, @Value("${successUrl}") UriTemplate successUrl,
            @Value("${receiveCopy}") boolean receiveCopy, AuthenticationService authentication, RestTemplate rest) {

        this.rest = rest;
        this.applyUrl = applyUrl;
        this.successUrl = successUrl;
        this.receiveCopy = receiveCopy;
        this.authentication = authentication;
    }

    private final AuthenticationService authentication;
    private final RestTemplate rest;
    private final String applyUrl;
    private final UriTemplate successUrl;
    private final boolean receiveCopy;

    @Override
    public void apply(Room room, Letter letter) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.COOKIE, authentication.getAuthenticatedCookies().toString());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("nachricht", letter.message());
        map.add("u_anrede", toAnrede(letter.candidate().gender()));
        map.add("vorname", letter.candidate().firstName().toString());
        map.add("nachname", letter.candidate().lastName().toString());
        map.add("email", letter.candidate().email().toString());
        map.add("telefon", letter.candidate().phone().toString());
        map.add("agb", "on");
        map.add("typ", "0");
        if (receiveCopy) {
            map.add("kopieanmich", "on");
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        LOGGER.debug("Request: {}", request);
        ResponseEntity<String> response = rest.postForEntity(applyUrl, request, String.class, room.id());
        LOGGER.debug("Response code: {}", response.getStatusCodeValue());
        if (response.getStatusCodeValue() != 303) {
            throw new IOException(String.format("Couldn't apply for %s, response status code was %d", room,
                    response.getStatusCodeValue()));
        }

        URI redirect = response.getHeaders().getLocation();
        LOGGER.debug("redirect: {}", redirect);
        if (redirect == null) {
            throw new IOException(String.format("Couldn't apply for %s, no redirect", room));
        }

        if (!successUrl.matches(redirect.toString())) {
            throw new IOException(String.format("Couldn't apply for %s, redirect was %s", room, redirect));
        }
        Map<String, String> redirectParameters = successUrl.match(redirect.toString());
        if (StringUtils.isEmpty(redirectParameters.get("roomId"))) {
            throw new IOException(
                    String.format("Couldn't apply for %s, no room in redirect, redirect was %s", room, redirect));
        }

        RoomId redirectedRoomId = new RoomId(redirectParameters.get("roomId"));
        if (!redirectedRoomId.equals(room.id())) {
            throw new IllegalStateException(String.format("Couldn't apply for %s, but applied for a different room %s",
                    room, redirectedRoomId));
        }
    }

    private static String toAnrede(Gender gender) {
        switch (gender) {
        case MALE:
            return "1";

        case FEMALE:
            return "2";

        default:
            throw new IllegalStateException("Unsupported gender " + gender);
        }
    }

}
