package de.malkusch.getroom.infrastructure;

import static java.util.Objects.requireNonNull;

import java.net.HttpCookie;
import java.util.List;

interface AuthenticationService {

    AuthenticationCookies getAuthenticatedCookies();

    static final class AuthenticationCookies {

        private final List<HttpCookie> cookies;

        AuthenticationCookies(List<HttpCookie> cookies) {
            if (requireNonNull(cookies).isEmpty()) {
                throw new IllegalArgumentException("Authentication cookies must not be empty");
            }
            this.cookies = cookies;
        }

        List<HttpCookie> cookies() {
            return cookies;
        }

        @Override
        public String toString() {
            return cookies.stream().map(HttpCookie::toString).reduce((c1, c2) -> c1 + ";" + c2).get();
        }
    }

}
