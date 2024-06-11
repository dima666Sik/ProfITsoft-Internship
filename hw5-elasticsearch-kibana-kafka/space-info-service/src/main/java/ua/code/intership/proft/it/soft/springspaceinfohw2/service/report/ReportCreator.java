package ua.code.intership.proft.it.soft.springspaceinfohw2.service.report;

import org.springframework.data.domain.Page;

import java.io.File;
import java.util.function.IntFunction;

public interface ReportCreator<T> {
    File createReport(IntFunction<Page<T>> pageFunction, String fileName, String[] columnTitles);
}
