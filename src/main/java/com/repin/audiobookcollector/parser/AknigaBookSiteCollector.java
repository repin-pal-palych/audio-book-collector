package com.repin.audiobookcollector.parser;

import com.repin.audiobookcollector.model.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.repin.audiobookcollector.model.Book.Builder.book;

public class AknigaBookSiteCollector implements BookSiteCollector {

    private final PageLoader pageLoader;

    public AknigaBookSiteCollector() {
        this.pageLoader = new PageLoader();
    }

    @Override
    public Collection<Book> collectPages(String baseUrl, Integer pagesToLoad) {
        List<Book> allBooks = new ArrayList<>();

        for (int i = 1; i <= pagesToLoad; i++) {
            String pageUrl = (i == 1) ? baseUrl : baseUrl + "/page" + i + "/";

            System.out.println("Загружаем страницу: " + pageUrl);

            Document pageHtml = pageLoader.loadPage(pageUrl);
            List<Book> booksOnPage = parsePage(pageHtml);

            System.out.println("Найдено книг на странице " + i + ": " + booksOnPage.size());

            allBooks.addAll(booksOnPage);
        }
        return allBooks;
    }

    @Override
    public ArrayList<Book> parsePage(Document htmlPage) {
        Elements containers = htmlPage.select("div.content__main__articles--item");

        final var books = new ArrayList<Book>();
        for (Element container : containers) {
            Element link = container.selectFirst("a.content__article-main-link");
            if (link == null) continue;

            String bookUrl = link.absUrl("href");

            Element titleElement = link.selectFirst("h2.caption__article-main");
            String fullTitle = titleElement != null ? titleElement.text() : "";
            String[] split = fullTitle.split("–", 2);
            String author = split.length > 0 ? split[0].trim() : "";
            String title = split.length > 1 ? split[1].trim() : "";

            Element descriptionElement = link.selectFirst("span[class*=description]");
            String description = descriptionElement != null ? descriptionElement.text().trim() : "";

            Element coverImg = container.selectFirst("div.article--cover img");
            String coverUrl = coverImg != null ? coverImg.absUrl("src") : "";

            final var book = book().title(title)
                .author(author)
                .description(description)
                .url(bookUrl)
                .cover(coverUrl)
                .build();

            books.add(book);
        }
        return books;
    }
}
