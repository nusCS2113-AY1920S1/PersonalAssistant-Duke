package duke.ui;

import java.util.Scanner;

import static duke.common.GeneralMessages.filePathIngredients;
import static duke.common.GeneralMessages.filePathBookings;
import static duke.common.GeneralMessages.filePathRecipes;
import static duke.common.GeneralMessages.MESSAGE_BYE;
import static duke.common.GeneralMessages.ERROR_MESSAGE_LOADING;
import static duke.common.GeneralMessages.DIVIDER;

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

    public void showIngredientLoadingError() {
        System.out.println(ERROR_MESSAGE_LOADING + filePathIngredients);
    }

    public void showBookingLoadingError() {
        System.out.println(ERROR_MESSAGE_LOADING + filePathBookings);
    }

    public void showRecipeLoadingError() {
        System.out.println(ERROR_MESSAGE_LOADING + filePathRecipes);
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }
}