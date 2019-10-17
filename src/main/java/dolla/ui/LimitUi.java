package dolla.ui;

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
}
