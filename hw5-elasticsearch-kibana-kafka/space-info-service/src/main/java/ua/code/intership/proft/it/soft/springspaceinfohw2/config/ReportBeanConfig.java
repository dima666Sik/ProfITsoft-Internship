package ua.code.intership.proft.it.soft.springspaceinfohw2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.report.CsvReportCreator;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.report.ReportCreator;

@Configuration
public class ReportBeanConfig {
    @Bean
    public ReportCreator<Planet> planetCsvReportCreator() {
        return new CsvReportCreator<>();
    }

}
