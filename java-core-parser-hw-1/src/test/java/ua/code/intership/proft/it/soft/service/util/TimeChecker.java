package ua.code.intership.proft.it.soft.service.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class TimeChecker {
    private TimeChecker() {
    }

    public static void timeTest(ProcessingConsumer assertionsConsumer) {
        long startTime = System.currentTimeMillis();
        assertionsConsumer.acceptAndProcessing();
        long endTime = System.currentTimeMillis();
        log.info("Work time this test: " + ((endTime - startTime) / 1000.0) + " .s");
    }

}
