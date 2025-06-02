package com.repin.audiobookcollector.collector;

import com.repin.audiobookcollector.model.Book;
import com.repin.audiobookcollector.parser.BookCollectorResolver;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BookCollector {

    private final BookCollectorResolver bookCollectorResolver;

    public BookCollector(BookCollectorResolver bookCollectorResolver) {
        this.bookCollectorResolver = bookCollectorResolver;
    }

    public List<Book> fetchBooks(String url, Integer pagesToLoad) {
        try {
            final var collector = bookCollectorResolver.resolve(url);
            return new ArrayList<>(collector.collectPages(url, pagesToLoad));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Не получилось распарсить книги: " + e.getMessage());
        }
    }
}
