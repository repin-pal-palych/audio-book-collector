package com.repin.audiobookcollector.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class PageLoader {

    public Document loadPage(String url) {
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
