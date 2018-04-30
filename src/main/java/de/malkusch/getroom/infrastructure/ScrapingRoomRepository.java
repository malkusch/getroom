package de.malkusch.getroom.infrastructure;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.collections4.map.LRUMap;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import de.malkusch.getroom.model.City;
import de.malkusch.getroom.model.CreationDate;
import de.malkusch.getroom.model.District;
import de.malkusch.getroom.model.Price;
import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.RoomId;
import de.malkusch.getroom.model.RoomRepository;

@Service
class ScrapingRoomRepository implements RoomRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingRoomRepository.class);

    ScrapingRoomRepository(@Value("${searchUrl}") UriTemplate searchUrl, @Value("${userAgent}") String userAgent,
            @Value("${timeout}") String timeout) throws IOException {

        this.searchUrl = searchUrl;
        this.userAgent = userAgent;
        this.timeout = Duration.parse(timeout);
        initCookies();
    }

    @Override
    public Stream<Room> findNewRooms(City city, District[] districts, Price maxPrice, CreationDate since)
            throws IOException {
        String url = searchUrl(city, districts, maxPrice);
        cookies.put("last_city", city.toString());
        Document document = download(url).parse();
        return document.select("*[id^=liste-details-ad-hidden-]").stream().map(this::toRoom)
                .filter(r -> r.createdAt().isAfter(since));
    }

    private void initCookies() throws IOException {
        cookies = new HashMap<>();
        String home = UriComponentsBuilder.fromUriString(searchUrl.toString()).replacePath("/").replaceQuery(null)
                .build().toUriString();
        download(home);
    }

    private volatile Map<String, String> cookies;
    private final String userAgent;
    private final Duration timeout;

    private Response download(String url) throws IOException {
        Connection connection = Jsoup.connect(url);
        connection.userAgent(userAgent);
        connection.timeout((int) timeout.toMillis());
        connection.cookies(cookies);

        Response response = connection.execute();
        cookies.putAll(response.cookies());
        return response;
    }

    private static final Pattern ID_PATTERN = Pattern.compile("\\Qliste-details-ad-hidden-\\E(\\d+)");
    private final LRUMap<RoomId, Room> rooms = new LRUMap<>(1000);

    private Room toRoom(Element element) {
        Matcher idMatcher = ID_PATTERN.matcher(element.attr("id"));
        if (!idMatcher.matches()) {
            throw new IllegalStateException("Could not extract id from " + element.attr("id"));
        }
        RoomId id = new RoomId(idMatcher.group(1));
        return rooms.computeIfAbsent(id, Room::new);
    }

    private final UriTemplate searchUrl;

    private String searchUrl(City city, District[] districts, Price maxPrice) {
        Map<String, Object> urlParameters = new HashMap<>();
        urlParameters.put("city", city);
        urlParameters.put("maxPrice", maxPrice);
        String districtParameters = Arrays.stream(districts).map(d -> "&ot%5B" + d + "%5D=" + d).reduce("",
                (d1, d2) -> d1 + d2);
        String expanded = searchUrl.expand(urlParameters) + districtParameters;
        LOGGER.debug(expanded);
        return expanded;
    }

}
