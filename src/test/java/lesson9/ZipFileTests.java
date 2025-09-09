package lesson9;


import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ZipFileTests {

    private final ClassLoader cl = ZipFileTests.class.getClassLoader();

    @Test
    @DisplayName("PDF файл: проверка содержимого и структуры")
    void shouldContainValidPdfInZip() throws IOException {
        byte[] pdfBytes = extractFileFromZip("dummy.pdf");
        PDF pdf = new PDF(new ByteArrayInputStream(pdfBytes));

        // Проверки прямо в тесте - для лучшей читаемости
        assertNotNull(pdf.text, "PDF должен содержать текст");
        assertTrue(pdf.text.contains("Dummy PDF file"), "PDF должен содержать заголовок");
        assertEquals(1, pdf.numberOfPages, "PDF должен иметь одну страницу");
    }

    @Test
    @DisplayName("Excel файл: проверка данных и структуры")
    void shouldContainValidExcelInZip() throws IOException {
        byte[] excelBytes = extractFileFromZip("excelfile.xls");
        XLS xls = new XLS(new ByteArrayInputStream(excelBytes));

        // Проверки прямо в тесте
        assertEquals("Sheet1", xls.excel.getSheetName(0), "Должна быть страница Sheet1");
        assertEquals("First Name", xls.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue(), "Правильный заголовок");
        assertEquals("Dulce", xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue(), "Правильные данные");
        assertTrue(xls.excel.getNumberOfSheets() > 0, "Должны быть листы");
    }

    @Test
    @DisplayName("CSV файл: проверка структуры и данных")
    void shouldContainValidCsvInZip() throws IOException, CsvException {
        byte[] csvBytes = extractFileFromZip("csvfile.csv");

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(csvBytes)))) {
            List<String[]> data = csvReader.readAll();

            // Проверки прямо в тесте
            assertEquals(6, data.size(), "Должно быть 6 строк (заголовок + данные)");
            assertArrayEquals(new String[]{"id", "name", "email", "department", "salary"}, data.get(0), "Правильные заголовки");
            assertEquals("John Doe", data.get(1)[1], "Правильное имя в первой записи");
            assertEquals("IT", data.get(1)[3], "Правильный отдел в первой записи");
        }
    }

    @Test
    @DisplayName("Архив должен содержать все ожидаемые файлы")
    void shouldContainAllRequiredFilesInZip() throws IOException {
        try (InputStream archiveStream = cl.getResourceAsStream("ziparchive.zip")) {
            assert archiveStream != null;
            try (ZipInputStream zis = new ZipInputStream(archiveStream)) {

                int pdfCount = 0, excelCount = 0, csvCount = 0;

                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().equals("dummy.pdf")) pdfCount++;
                    else if (entry.getName().equals("excelfile.xls")) excelCount++;
                    else if (entry.getName().equals("csvfile.csv")) csvCount++;
                }

                assertEquals(1, pdfCount, "Должен быть 1 PDF файл");
                assertEquals(1, excelCount, "Должен быть 1 Excel файл");
                assertEquals(1, csvCount, "Должен быть 1 CSV файл");
            }
        }
    }

    // ВСПОМОГАТЕЛЬНЫЙ МЕТОД (только для извлечения - не для проверок)
    private byte[] extractFileFromZip(String fileName) throws IOException {
        try (InputStream archiveStream = cl.getResourceAsStream("ziparchive.zip")) {
            assert archiveStream != null;
            try (ZipInputStream zis = new ZipInputStream(archiveStream)) {

                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().equals(fileName)) {
                        return zis.readAllBytes();
                    }
                }
                throw new IOException("Файл " + fileName + " не найден в архиве");
            }
        }
    }
}
