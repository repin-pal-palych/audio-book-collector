package com.repin.audiobookcollector.writer;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.repin.audiobookcollector.model.Book;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvWriter {

    public void saveBooks(List<Book> books) {
        try {
            Files.createDirectories(Paths.get("data"));
            FileWriter writer = new FileWriter("data/output.csv");
            StatefulBeanToCsv<Book> beanToCsv = new StatefulBeanToCsvBuilder<Book>(writer).build();
            beanToCsv.write(books);
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException("Не удалось сохранить книги: " + e.getMessage());
        }
    }
}
