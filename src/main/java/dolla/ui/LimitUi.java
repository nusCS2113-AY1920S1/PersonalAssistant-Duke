package dolla.ui;

import dolla.Ui;
import dolla.task.Limit;

/**
 * LimitUi is a class that handles all limit related user interactions.
 */
public class LimitUi extends Ui {

    public static void echoAddLimit(Limit currLimit) {
        System.out.println(line);
        System.out.println("\tGot it. I've added this limit: ");
        System.out.println("\t" + currLimit.getLogText());
        System.out.println(line);
    }

    public static void invalidSetCommandPrinter() {
        System.out.println(line);
        System.out.println("\tOOPS! Please follow the format set budget [AMOUNT] /every [DURATION]");
        System.out.println(line);
    }

    public static void echoRemoveLimit(String type, String duration) {
        System.out.println(line);
        System.out.println("\tGot it. I've removed this Limit: ");
        System.out.println("\t" + type + " /every " + duration);
        System.out.println(line);
    }

    public static void invalidRemoveCommandPrinter() {
        System.out.println(line);
        System.out.println("\tOOPS! Please follow the format remove budget /every [DURATION]");
        System.out.println(line);
    }
}
