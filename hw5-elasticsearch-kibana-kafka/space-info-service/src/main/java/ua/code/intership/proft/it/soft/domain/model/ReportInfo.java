package ua.code.intership.proft.it.soft.domain.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReportInfo {
    private volatile int numberOfSuccessfulExecutions;
    private volatile int numberOfUnsuccessfulExecutions;

    public synchronized void incrementNumberOfSuccessfulExecutions() {
        this.numberOfSuccessfulExecutions++;
    }

    public synchronized void incrementNumberOfUnsuccessfulExecutions() {
        this.numberOfUnsuccessfulExecutions++;
    }
}
