package ua.code.intership.proft.it.soft.springspaceinfohw2.service.report;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;

import java.util.HashMap;
import java.util.Map;
/**
 * Factory class for creating {@link ReportCreator} instances for generating reports related to planets.
 */
@Component
@Log4j2
public class PlanetReportCreatorFactory implements ReportCreatorFactory {
    private static final String CSV_FORMAT = "csv";
    private final Map<String, ReportCreator<Planet>> reportCreators;

    @Autowired
    public PlanetReportCreatorFactory(@Qualifier("planetCsvReportCreator") ReportCreator<Planet> csvReportCreator) {
        reportCreators = new HashMap<>();
        reportCreators.put(CSV_FORMAT, csvReportCreator);
    }
    /**
     * Retrieves the appropriate ReportCreator based on the given format.
     *
     * @param format the format of the report (e.g., "csv")
     * @return the ReportCreator for the specified format
     * @throws IllegalArgumentException if the format is unknown
     */
    public ReportCreator<Planet> getReportCreator(String format) {
        ReportCreator<Planet> reportCreator = reportCreators.get(format.toLowerCase());
        if (reportCreator == null) {
            log.error("Unknown report format: " + format);
            throw new IllegalArgumentException("Unknown report format: " + format);
        }
        return reportCreator;
    }
}
