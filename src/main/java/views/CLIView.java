package views;

import controllers.ConsoleInputController;

import java.io.IOException;
import java.util.Scanner;

public class CLIView {
    private final String LOGO = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
    private final String HORILINE = "____________________________________________________________";

    private ConsoleInputController consoleInputController;

    public CLIView() {
        this.consoleInputController = new ConsoleInputController(this);
    }

    public void start() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println(HORILINE);
        System.out.println("\tHello! I'm Duke");
        System.out.println("\tWhat can I do for you?");
        System.out.println(HORILINE);

        while (true) {
            String command = sc.nextLine();
            consoleInputController.onCommandReceived(command);
        }
    }

    public void end() {
        System.out.println("\t Bye. Hope to see you again soon!");
        System.exit(0);
    }
}
