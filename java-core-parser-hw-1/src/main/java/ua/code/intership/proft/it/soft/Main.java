package ua.code.intership.proft.it.soft;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Integer> statisticsDtoSet = new HashSet<>();
        statisticsDtoSet.add(0);
        statisticsDtoSet.add(1);
        System.out.println(statisticsDtoSet.stream()
                        .anyMatch(el -> el.equals(0)));
    }
}
