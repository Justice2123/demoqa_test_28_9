import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pages.OffersKey;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class CheckFilesTest {
    private ClassLoader cl = CheckFilesTest.class.getClassLoader();


    @Test
    void checkPdfFileTest() throws Exception {
        try (ZipInputStream zipFile = new ZipInputStream(
                cl.getResourceAsStream("projectFiles.zip")
        )) {
            ZipEntry entry;
            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.getName().equals("pass.pdf")) {
                    PDF pdfFile = new PDF(zipFile);
                    assertThat(pdfFile.text.trim()).isEqualTo("WB-GI-93560283");
                }
            }
        }
    }


    @Test
    void checkXlsFileTest() throws Exception {
        try (ZipInputStream zipFile = new ZipInputStream(
                cl.getResourceAsStream("projectFiles.zip")
        )) {
            ZipEntry entry;
            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.getName().equals("price.xlsx")) {
                    XLS xlsFile = new XLS(zipFile);
                    String xlsRow = xlsFile.excel.getSheetAt(0).getRow(14).getCell(1).getStringCellValue(),
                            shkGoods = xlsFile.excel.getSheetAt(0).getRow(23).getCell(3).getStringCellValue();
                    Double priceGoods = xlsFile.excel.getSheetAt(0).getRow(23).getCell(35).getNumericCellValue();


                    Assertions.assertTrue(xlsRow.contains(
                            "Счет на оплату № 124468 от 25 марта 2024 г. Договор № 2 174 606 от 01.03.2024")
                    );
                    Assertions.assertTrue(shkGoods.contains("6900079118207;"));
                    Assertions.assertEquals(179.0, priceGoods);
                }
            }
        }
    }


    @Test
    void checkCsvFileTest() throws Exception {
        try (ZipInputStream zipFile = new ZipInputStream(
                cl.getResourceAsStream("projectFiles.zip")
        )) {
            ZipEntry entry;
            while ((entry = zipFile.getNextEntry()) != null) {
                if (entry.getName().equals("sample4.csv")) {
                    CSVReader csvFile = new CSVReader(new InputStreamReader(zipFile));
                    List<String[]> data = csvFile.readAll();
                    assertThat(data.get(0)).isEqualTo(new String[]{"Game Number", "Game Length"});
                    assertThat(data.get(1)).isEqualTo(new String[]{"1", "23"});

                }
            }
        }
    }


    @Test
    void checkJsonFileTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("date.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            OffersKey offersKey = objectMapper.readValue(is, OffersKey.class);
            assertThat(offersKey.getOffers().get(0).getBarcode()).isEqualTo("7501031311309");
            assertThat(offersKey.getOffers().get(1).getId()).isEqualTo("22222");
            assertThat(offersKey.getOffers().get(2).getPrice_new()).isEqualTo(10);
        }


    }


}



