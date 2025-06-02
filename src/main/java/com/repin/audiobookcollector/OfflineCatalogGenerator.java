package com.repin.audiobookcollector;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class OfflineCatalogGenerator {

    private static final String OUTPUT_HTML = "data/catalog.html";
    private static final String CSV_FILE = "data/output.csv";

    public static void main(String[] args) {
        try {
            List<String[]> rows = readCsv(CSV_FILE);
            generateHtml(rows, OUTPUT_HTML);
            System.out.println("Каталог сгенерирован: " + OUTPUT_HTML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> readCsv(String csvFile) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            return reader.readAll();
        }
    }

    private static void generateHtml(List<String[]> rows, String outputHtml) throws IOException {
        if (rows.isEmpty()) {
            System.out.println("CSV пустой, нечего генерировать.");
            return;
        }

        // Заголовки из первой строки
        String[] headers = rows.get(0);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputHtml))) {
            writer.write("<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n<meta charset=\"UTF-8\">\n");
            writer.write("<title>Каталог аудиокниг</title>\n");
            writer.write("<style>\n");
            writer.write("body { font-family: Arial, sans-serif; background:#f4f4f4; margin:20px; }\n");
            writer.write(".book { background: #fff; border: 1px solid #ddd; margin-bottom: 15px; padding: 15px; display: flex; }\n");
            writer.write(".cover { margin-right: 15px; }\n");
            writer.write(".cover img { max-width: 150px; height: auto; }\n");
            writer.write(".info { max-width: 800px; }\n");
            writer.write(".title { font-weight: bold; font-size: 1.2em; margin-bottom: 5px; }\n");
            writer.write(".author { font-style: italic; margin-bottom: 10px; }\n");
            writer.write(".description { color: #333; }\n");
            writer.write("</style>\n</head>\n<body>\n");

            writer.write("<h1>Каталог аудиокниг</h1>\n");

            // Перебираем все строки, кроме заголовка
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);

                // Полагаю, что колонки идут так:
                // 0 - Автор, 1 - Название, 2 - Описание, 3 - Путь к обложке (относительный)

                String author = getSafe(row, 0);
                String coverPath = getSafe(row, 1);
                String description = getSafe(row, 2);
                String title = getSafe(row, 3);

                writer.write("<div class=\"book\">\n");

                if (!coverPath.isEmpty()) {
                    writer.write("<div class=\"cover\"><img src=\"" + escapeHtml(coverPath) + "\" alt=\"" + escapeHtml(title) + "\"></div>\n");
                } else {
                    writer.write("<div class=\"cover\"><div style=\"width:150px; height:220px; background:#ccc; display:flex; align-items:center; justify-content:center; color:#888;\">Нет обложки</div></div>\n");
                }

                writer.write("<div class=\"info\">\n");
                writer.write("<div class=\"title\">" + escapeHtml(title) + "</div>\n");
                writer.write("<div class=\"author\">" + escapeHtml(author) + "</div>\n");
                writer.write("<div class=\"description\">" + escapeHtml(description) + "</div>\n");
                writer.write("</div>\n</div>\n");
            }

            writer.write("</body>\n</html>");
        }
    }

    private static String getSafe(String[] array, int index) {
        if (array.length > index && array[index] != null) {
            return array[index];
        }
        return "";
    }

    // Простая эскейпинг HTML
    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
}