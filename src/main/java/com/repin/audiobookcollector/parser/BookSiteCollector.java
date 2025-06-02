package com.repin.audiobookcollector.parser;

import com.repin.audiobookcollector.model.Book;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collection;

public interface BookSiteCollector {

    Collection<Book> collectPages(String url, Integer pagesToLoad);

    ArrayList<Book> parsePage(Document htmlPage);
}
