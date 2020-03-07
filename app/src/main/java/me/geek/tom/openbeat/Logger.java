package me.geek.tom.openbeat;

public class Logger {

    private static final String PREFIX = "[ OPENBEAT ]";
    private static final String COLOUR_CODE = "\u001b[35m";
    private static final String RESET = "\u001b[0m";

    public static void info(String message) {
        System.out.println(COLOUR_CODE + PREFIX + " " + message + RESET);
    }
}
