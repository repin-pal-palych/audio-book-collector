package com.repin.audiobookcollector.model;

import com.opencsv.bean.CsvBindByName;

public record Book(@CsvBindByName(column = "Title") String title,
                   @CsvBindByName(column = "Author") String author,
                   @CsvBindByName(column = "Description") String description,
                   @CsvBindByName(column = "URL") String url,
                   @CsvBindByName(column = "Cover") String cover) {

    public Book(Builder builder) {
        this(builder.title, builder.author, builder.description, builder.url, builder.cover);
    }

    public static class Builder {
        private String title;
        private String author;
        private String description;
        private String url;
        private String cover;

        private Builder() {
        }

        public static Builder book() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder cover(String cover) {
            this.cover = cover;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
