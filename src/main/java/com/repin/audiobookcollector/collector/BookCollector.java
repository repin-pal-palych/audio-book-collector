package com.repin.audiobookcollector.collector;

import com.repin.audiobookcollector.model.Book;
import com.repin.audiobookcollector.parser.BookParserResolver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class BookCollector {

    private final BookParserResolver bookParserResolver;

    public BookCollector(BookParserResolver bookParserResolver) {
        this.bookParserResolver = bookParserResolver;
    }

    public List<Book> fetchBooks(String url) {
        Document htmlPage = loadPage(url);

        try {
            final var parser = bookParserResolver.resolve(url);
            return new ArrayList<>(parser.parsePage(htmlPage));
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
