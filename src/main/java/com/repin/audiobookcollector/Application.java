package com.repin.audiobookcollector;

import com.repin.audiobookcollector.collector.BookCollector;
import com.repin.audiobookcollector.model.Book;
import com.repin.audiobookcollector.parser.BookParserResolver;
import com.repin.audiobookcollector.writer.CsvWriter;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        final var collector = new BookCollector(new BookParserResolver());
        List<Book> books = collector.fetchBooks("https://akniga.org/label/genre/litrpg");

        new CsvWriter().saveBooks(books);

        System.out.println("Сохранено книг: " + books.size());
    }
}