package ua.code.intership.proft.it.soft.model.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StatisticsDto {
    private String attribute;
    private volatile int numberOfRepetitions;

    public synchronized void incrementNumberOfRepetitions() {
        this.numberOfRepetitions++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsDto that = (StatisticsDto) o;
        return Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }
}
