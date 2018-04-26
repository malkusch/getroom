package de.malkusch.getroom.infrastructure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.collections4.map.LRUMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import de.malkusch.getroom.model.City;
import de.malkusch.getroom.model.CreationDate;
import de.malkusch.getroom.model.Price;
import de.malkusch.getroom.model.Room;
import de.malkusch.getroom.model.RoomId;
import de.malkusch.getroom.model.RoomRepository;

@Service
class ScrapingRoomRepository implements RoomRepository {

    private final UriTemplate searchUrl;

    ScrapingRoomRepository(@Value("${searchUrl}") UriTemplate searchUrl) {
        this.searchUrl = searchUrl;
    }

    @Override
    public Stream<Room> findNewRooms(City city, Price maxPrice, CreationDate since) throws IOException {
        String url = searchUrl(city, maxPrice);
        Document document = Jsoup.connect(url).get();
        return document.select("*[id^=liste-details-ad-hidden-]").stream().map(this::toRoom)
                .filter(r -> r.createdAt().isAfter(since));
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

    private String searchUrl(City city, Price maxPrice) {
        Map<String, Object> urlParameters = new HashMap<>();
        urlParameters.put("city", city);
        urlParameters.put("maxPrice", maxPrice);
        return searchUrl.expand(urlParameters).toString();
    }

}
