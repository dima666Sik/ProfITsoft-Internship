package ua.code.intership.proft.it.soft.springspaceinfohw2.service.report;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.exception.FileGenerationException;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.reflect.ClassMetadataProvider;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.IntFunction;

@RequiredArgsConstructor
public class CsvReportCreator<T> implements ReportCreator<T> {
    private static final String CSV_FILE_NAME = "planet_report_by_planetarySystemId";
    public static final String CSV = ".csv";

    public File createReport(IntFunction<Page<T>> pageFunction, String[] columnTitles) {
        try {
            File tempFile = File.createTempFile(CSV_FILE_NAME, CSV);
            PrintWriter printWriter = new PrintWriter(tempFile);
            CSVWriter writer = new CSVWriter(printWriter);

            int pageNumber = 0;
            boolean hasNextPage = true;

            writer.writeNext(columnTitles);

            while (hasNextPage) {
                Page<T> page = pageFunction.apply(pageNumber);

                for (T entity : page.getContent()) {
                    String[] fieldsDataFromGenericEntity = ClassMetadataProvider.extractFieldsDataFromGenericEntity(entity);
                    writer.writeNext(fieldsDataFromGenericEntity);
                }

                hasNextPage = page.hasNext();
                pageNumber++;
            }

            writer.close();
            printWriter.close();

            return tempFile;
        } catch (IOException e) {
            throw new FileGenerationException("Failed to generate planet report", e);
        }
    }
}
