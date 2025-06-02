package com.repin.audiobookcollector;

import com.repin.audiobookcollector.collector.BookCollector;
import com.repin.audiobookcollector.model.Book;
import com.repin.audiobookcollector.parser.BookCollectorResolver;
import com.repin.audiobookcollector.writer.CsvWriter;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        final var collector = new BookCollector(new BookCollectorResolver());
        List<Book> books = collector.fetchBooks("https://akniga.org/label/genre/litrpg", 10);

        new CsvWriter().saveBooks(books);

        System.out.println("Сохранено книг: " + books.size());
    }
}