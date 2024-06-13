package ua.code.intership.proft.it.soft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.code.intership.proft.it.soft.domain.model.Planet;
import ua.code.intership.proft.it.soft.service.report.CsvReportCreator;
import ua.code.intership.proft.it.soft.service.report.ReportCreator;

@Configuration
public class ReportBeanConfig {
    @Bean
    public ReportCreator<Planet> planetCsvReportCreator() {
        return new CsvReportCreator<>();
    }

}
