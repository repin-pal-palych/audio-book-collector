package com.repin.audiobookcollector.parser;

import com.repin.audiobookcollector.model.Book;

import java.util.Collection;

public interface BookSiteCollector {

    Collection<Book> collectPages(String url, Integer pagesToLoad);

}
