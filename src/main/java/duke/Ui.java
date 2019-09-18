package duke;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private Scanner scanner = new Scanner(System.in);

    /**
     * This is to show a welcome message on startup.
     * @return the welcome String.
     */
    public String showWelcome() {
        List<String> welcome = new ArrayList<>();
        welcome.add("Hello! I'm Duke");
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
     * This is to add the Duke UI wrapper to printed messages.
     * @param output A single string to be formatted.
     * @return the formatted output.
     */
    public String showFormatted(String output) {
        String horizontalLine = "____________________________________________________________";
        String formattedOutput = horizontalLine + "\n" + output + "\n" + horizontalLine;
        return formattedOutput;
    }

    /**
     * This is to add the Duke UI wrapper to printed messages.
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
}
