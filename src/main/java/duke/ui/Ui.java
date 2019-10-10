package duke.ui;

import java.util.Scanner;

import static duke.common.Messages.filePath;
import static duke.common.Messages.MESSAGE_BYE;
import static duke.common.Messages.ERROR_MESSAGE_LOADING;
import static duke.common.Messages.DIVIDER;

public class Ui {

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Display welcome message of the program.
     */
    public void showWelcome() {
        String logo =
            "      ___         _        \n"
            + "     |  _ \\ _   _| | _____ \n"
            + "     | | | | | | | |/ / _ \\\n"
            + "     | |_| | |_| |   <  __/\n"
            + "     |____/ \\__,_|_|\\_\\___|\n"
            + "\n";

        System.out.println(DIVIDER + logo + "     Hello! I'm Duke\n" + "     What can I do for you?\n" + DIVIDER);
    }

    public void showGoodbye() {
        System.out.println(MESSAGE_BYE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.print(DIVIDER);
    }

    public void showLoadingError() {
        System.out.println(ERROR_MESSAGE_LOADING + filePath);
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
