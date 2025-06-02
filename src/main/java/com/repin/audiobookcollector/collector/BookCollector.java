package com.repin.audiobookcollector.collector;

import com.repin.audiobookcollector.model.Book;
import com.repin.audiobookcollector.parser.BookCollectorResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
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

    private static Document loadPage(String url) {
        try {
            return Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить страницу, url: " + url + " error: " + e.getMessage());
        }
    }
}
