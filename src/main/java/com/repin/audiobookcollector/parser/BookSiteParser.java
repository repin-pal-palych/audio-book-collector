package com.repin.audiobookcollector.parser;

import com.repin.audiobookcollector.model.Book;
import org.jsoup.nodes.Document;

import java.util.Collection;

public interface BookSiteParser {

    Collection<Book> parsePage(Document htmlPage);

}
