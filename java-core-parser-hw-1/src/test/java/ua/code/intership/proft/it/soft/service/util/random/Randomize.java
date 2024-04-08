package ua.code.intership.proft.it.soft.service.util.random;

public final class Randomize {
    private Randomize() {
    }

    public static int generateRandomize(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public static double generateRandomize(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public static boolean generateRandomize() {
        return Math.random() > 0.5;
    }
}
