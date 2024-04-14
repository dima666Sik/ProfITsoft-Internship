package ua.code.intership.proft.it.soft.ui.util;

public final class ConsolePrinter {
    private ConsolePrinter() {
    }
    public static void printMessageWithNewLine(String message){
        System.out.println(message);
    }

    public static void printMessageWithoutNewLine(String message){
        System.out.print(message);
    }
}
