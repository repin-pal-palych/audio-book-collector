package com.repin.audiobookcollector.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class BookParserResolver {

    private final Map<String, BookSiteParser> PARSERS = Map.of("akniga.org", new AknigaBookSiteParser());

    public BookSiteParser resolve(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String host = uri.getHost();

        if (host == null) {
            throw new IllegalArgumentException("Некорректный URL: " + url);
        }
        host = host.toLowerCase();
        for (Map.Entry<String, BookSiteParser> entry : PARSERS.entrySet()) {
            if (host.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        throw new UnsupportedOperationException("Парсер для сайта " + host + " не реализован");
    }
}
