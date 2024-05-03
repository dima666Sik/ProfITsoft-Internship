package ua.code.intership.proft.it.soft.springspaceinfohw2.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlanetReportCreatorFactory implements ReportCreatorFactory {
    private static final String CSV_FORMAT = "csv";
    private final Map<String, ReportCreator<Planet>> reportCreators;

    @Autowired
    public PlanetReportCreatorFactory(@Qualifier("planetCsvReportCreator") ReportCreator<Planet> csvReportCreator) {
        reportCreators = new HashMap<>();
        reportCreators.put(CSV_FORMAT, csvReportCreator);
    }

    public ReportCreator<Planet> getReportCreator(String format) {
        ReportCreator<Planet> reportCreator = reportCreators.get(format.toLowerCase());
        if (reportCreator == null) {
            throw new IllegalArgumentException("Unknown report format: " + format);
        }
        return reportCreator;
    }
}
