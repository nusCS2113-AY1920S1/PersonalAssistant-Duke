package frontend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AsciiColours {

    // Color code strings from:
    // http://www.topmudsites.com/forums/mud-coding/413-java-ansi.html

    //    static String SANE = "\u001B[0m" + "\u001B[30m" + "\u001B[47m";
    public static String SANE = "\u001B[0m";

    public static String HIGH_INTENSITY = "\u001B[1m";
    public static String LOW_INTENSITY = "\u001B[2m";

    public static String ITALIC = "\u001B[3m";
    public static String UNDERLINE = "\u001B[4m";
    public static String BLINK = "\u001B[5m";
    public static String RAPID_BLINK = "\u001B[6m";
    public static String REVERSE_VIDEO = "\u001B[7m";
    public static String INVISIBLE_TEXT = "\u001B[8m";

    public static String BLACK = "\u001B[30m";
    public static String RED = "\u001B[31m" + "\u001B[1m";
    public static String GREEN = "\u001B[32m" + "\u001B[1m";
    public static String YELLOW = "\u001B[33m" + "\u001B[1m";
    public static String BLUE = "\u001B[34m" + "\u001B[1m";
    public static String MAGENTA = "\u001B[35m" + "\u001B[1m";
    public static String CYAN = "\u001B[36m" + "\u001B[1m";
    public static String WHITE = "\u001B[37m" + "\u001B[1m";

    public static String BACKGROUND_BLACK = "\u001B[40m";
    public static String BACKGROUND_RED = "\u001B[41m";
    public static String BACKGROUND_GREEN = "\u001B[42m";
    public static String BACKGROUND_YELLOW = "\u001B[43m";
    public static String BACKGROUND_BLUE = "\u001B[44m";
    public static String BACKGROUND_MAGENTA = "\u001B[45m";
    public static String BACKGROUND_CYAN = "\u001B[46m";
    public static String BACKGROUND_WHITE = "\u001B[47m";

    public static String DONE = GREEN;
    public static String NOT_DONE = HIGH_INTENSITY;
    static String  HIGHLIGHT = "\u001B[0m"  + "\u001B[1m" + BACKGROUND_GREEN;
    static String  ERROR = "\u001B[0m"  + "\u001B[1m" + BACKGROUND_RED;

    /**
     * Deactivates the ANSI colours. Used if the operating system is determined to be Windows
     */
    public static void inActivate() {
        SANE = "";

        HIGH_INTENSITY = "";
        LOW_INTENSITY = "";

        ITALIC = "";
        UNDERLINE = "";
        BLINK = "";
        RAPID_BLINK = "";
        REVERSE_VIDEO = "";
        INVISIBLE_TEXT = "";

        BLACK = "";
        RED = "";
        GREEN = "";
        YELLOW = "";
        BLUE = "";
        MAGENTA = "";
        CYAN = "";
        WHITE = "";

        BACKGROUND_BLACK = "";
        BACKGROUND_RED = "";
        BACKGROUND_GREEN = "";
        BACKGROUND_YELLOW = "";
        BACKGROUND_BLUE = "";
        BACKGROUND_MAGENTA = "";
        BACKGROUND_CYAN = "";
        BACKGROUND_WHITE = "";
        DONE = "";
        NOT_DONE = "";
        HIGHLIGHT = "";
        ERROR = "";
    }

    
}