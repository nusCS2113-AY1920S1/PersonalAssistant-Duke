import java.util.Scanner;

import Task.TaskList;

public final class Duke {
    /**
     * Created a user interface object.
     */
    private static Ui ui = new Ui();

    private Duke() {
        //not called
    }

    /**
     * Function starts the running of Duke.
     */
    public static void run() {
        while (true) {

            Scanner sc = new Scanner(System.in);
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.equals("bye")) {
                    ui.goodbye();
                    break;
                }
                ui.readCommand(input);
            }
        }
    }

    /**
     * This program runs the main duke program.
     *
     * @param args I/O
     */
    public static void main(final String[]  args) {
        ui.welcome();
        TaskList.addAllList();
        run();
    }

}
