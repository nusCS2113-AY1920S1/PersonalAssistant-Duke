package spinbox;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private Scanner scanner = new Scanner(System.in);
    boolean gui;

    public Ui(boolean gui) {
        this.gui = gui;
    }

    /**
     * This is to show a welcome message on startup.
     * @return the welcome String.
     */
    public String showWelcome() {
        List<String> welcome = new ArrayList<>();
        welcome.add("Hello! I'm SpinBox");
        welcome.add("What can I do for you?");
        return showFormatted(welcome);
    }

    /**
     * This is to show a goodbye message on exit.
     */
    public String showGoodbye() {
        return showFormatted("Bye. Hope to see you again soon!");
    }

    /**
     * This is to add the SpinBox UI wrapper to printed messages.
     * @param output A single string to be formatted.
     * @return the formatted output.
     */
    public String showFormatted(String output) {
        String formattedOutput;
        if (!gui) {
            formattedOutput = output;
        } else {
            formattedOutput = HORIZONTAL_LINE + "\n" + output + "\n" + HORIZONTAL_LINE;
        }
        return formattedOutput;
    }

    /**
     * This is to add the SpinBox UI wrapper to printed messages.
     * @param output A List of strings that will be accumulated into a Single string.
     * @return the formatted String.
     */
    public String showFormatted(List<String> output) {
        String formattedOutput = "";
        for (String outputItem : output) {
            formattedOutput = formattedOutput.concat(outputItem + "\n");
        }
        if (formattedOutput.length() > 0) {
            return showFormatted(formattedOutput.substring(0, formattedOutput.length() - 1));
        } else {
            return showFormatted("");
        }
    }

    public void print(String input) {
        System.out.println(input);
    }

    public String readInput() {
        return scanner.nextLine();
    }

    /**
     * Returns the current page.
     * @param pageTrace the structure the pages are stored.
     * @return a string of the page hierarchy.
     */
    public String showPage(ArrayDeque<String> pageTrace) {
        ArrayDeque<String> tempPageTrace = pageTrace.clone();
        String trace = "";
        trace = trace.concat("Page: ");

        while (tempPageTrace.size() > 0) {
            trace = trace.concat("/" + tempPageTrace.getLast());
            tempPageTrace.removeLast();
        }
        return trace;
    }
}