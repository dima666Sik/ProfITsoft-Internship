package ua.code.intership.proft.it.soft.springspaceinfohw2.service.report;

import ua.code.intership.proft.it.soft.springspaceinfohw2.model.Planet;

public interface ReportCreatorFactory {
    ReportCreator<Planet> getReportCreator(String format);
}
