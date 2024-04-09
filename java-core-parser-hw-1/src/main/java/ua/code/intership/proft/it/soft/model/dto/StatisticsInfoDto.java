package ua.code.intership.proft.it.soft.model.dto;

import lombok.*;

@Data
@Builder
public class StatisticsInfoDto<T extends Comparable<T>> {
    private T attribute;
    @EqualsAndHashCode.Exclude
    private volatile int numberOfRepetitions;

    public synchronized void incrementNumberOfRepetitions() {
        this.numberOfRepetitions++;
    }
}
