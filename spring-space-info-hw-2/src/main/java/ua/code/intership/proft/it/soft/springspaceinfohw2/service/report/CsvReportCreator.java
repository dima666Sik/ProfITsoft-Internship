package ua.code.intership.proft.it.soft.springspaceinfohw2.service.report;

import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.exception.FileGenerationException;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.reflect.ClassMetadataProvider;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.IntFunction;

@RequiredArgsConstructor
@Log4j2
public class CsvReportCreator<T> implements ReportCreator<T> {
    public static final String CSV = ".csv";

    public File createReport(IntFunction<Page<T>> pageFunction, String fileName, String[] columnTitles) {
        try {
            File tempFile = File.createTempFile(fileName, CSV);
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

            log.info("The .csv report file was successfully created!");

            return tempFile;
        } catch (IOException e) {
            log.error("Failed to generate planet report", e);
            throw new FileGenerationException("Failed to generate planet report", e);
        }
    }
}
