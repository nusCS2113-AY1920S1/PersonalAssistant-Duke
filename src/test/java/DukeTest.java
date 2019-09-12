import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DukeTest {
    private Scanner scanner = new Scanner(System.in);

    /**
     * This is to show a welcome message on startup.
     */
    public void showWelcome() {
        List<String> welcome = new ArrayList<>();
        welcome.add("Hello! I'm Duke.Duke");
        welcome.add("What can I do for you?");
        showFormatted(welcome);
    }

    /**
     * This is to show a goodbye message on exit.
     */
    public void showGoodbye() {
        showFormatted("Bye. Hope to see you again soon!");
    }

    /**
     * This is to add the Duke UI wrapper to printed messages.
     * @param output A single string to be printed.
     */
    public void showFormatted(String output) {
        String horizontalLine = "____________________________________________________________";
        String formattedOutput = horizontalLine + "\n" + output + "\n" + horizontalLine;
        System.out.println(formattedOutput);
    }

    /**
     * This is to add the Duke UI wrapper to printed messages.
     * @param output A List of strings that will be accumulated into a Single string.
     */
    public void showFormatted(List<String> output) {
        String formattedOutput = "";
        for (String outputItem : output) {
            formattedOutput = formattedOutput.concat(outputItem + "\n");
        }
        showFormatted(formattedOutput);
    }

    public String readInput() {
        return scanner.nextLine();
    }
}
