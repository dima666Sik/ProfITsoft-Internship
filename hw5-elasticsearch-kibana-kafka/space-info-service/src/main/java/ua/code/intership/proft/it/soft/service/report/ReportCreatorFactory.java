package ua.code.intership.proft.it.soft.service.report;

import ua.code.intership.proft.it.soft.domain.model.Planet;

public interface ReportCreatorFactory {
    ReportCreator<Planet> getReportCreator(String format);
}
