package ua.code.intership.proft.it.soft.service.util;

public final class TimeChecker {
    private TimeChecker() {}

    public static void timeTest(ProcessingConsumer assertionsConsumer) {
        long startTime = System.nanoTime();
        assertionsConsumer.acceptAndProcessing();
        long endTime = System.nanoTime();
        System.out.println("Work time this test: " + (endTime - startTime));
    }

}
