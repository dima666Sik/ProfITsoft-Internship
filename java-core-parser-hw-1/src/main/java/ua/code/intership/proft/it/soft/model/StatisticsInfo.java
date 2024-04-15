package ua.code.intership.proft.it.soft.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
public class StatisticsInfo<T extends Comparable<T>> {
    private T attribute;
    @EqualsAndHashCode.Exclude
    private volatile int numberOfRepetitions;

    public synchronized void incrementNumberOfRepetitions() {
        this.numberOfRepetitions++;
    }
}
