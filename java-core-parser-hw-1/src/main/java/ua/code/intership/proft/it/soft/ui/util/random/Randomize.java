package ua.code.intership.proft.it.soft.ui.util.random;

import java.util.Random;

public class Randomize {
    private static final Random random = new Random();

    private Randomize() {
    }

    public static int generateRandomize(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static double generateRandomize(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static boolean generateRandomize() {
        return random.nextBoolean();
    }
}
