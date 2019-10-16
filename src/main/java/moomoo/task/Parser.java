package moomoo.task;

import moomoo.command.AddCategoryCommand;
import moomoo.command.BudgetCommand;
import moomoo.command.Command;
import moomoo.command.ExitCommand;
import moomoo.command.GraphCommand;
import moomoo.command.ListCategoryCommand;
import moomoo.command.DeleteCategoryCommand;

import java.util.Scanner;

/**
 * Takes in a string and parses it to return a valid command to be ran.
 */
public class Parser {
    /**
     * Takes in input from user and returns a command based on the input given. All parsing of input
     * should be done here including separating a line of command into parts for each command.
     * @param input String given by the user
     * @param ui MooMoo's ui
     * @return The command object corresponding to the user input
     * @throws MooMooException Thrown when an invalid input is given
     */

    public static Command parse(String input, Ui ui) throws MooMooException {
        Scanner scanner = new Scanner(input);
        String commandType = scanner.next();
        switch (commandType) {
        case ("bye"): return new ExitCommand(true, "");
        case ("budget"): return new BudgetCommand(false, input);
        case ("categories"): return new ListCategoryCommand(false, "");
        case ("add"): return parseAdd(scanner, ui);
        case ("delete"): return parseDelete(scanner, ui);
        case ("graph"): return new GraphCommand(input);
        default: throw new MooMooException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static Command parseDelete(Scanner scanner, Ui ui) throws MooMooException {
        switch (scanner.next()) {
        case ("category"): return new DeleteCategoryCommand();
        default: throw new MooMooException("Sorry I did not recognize that command.");
        }
    }

    private static Command parseAdd(Scanner scanner, Ui ui) throws MooMooException {
        switch (scanner.next()) {
        case ("category"): return parseAddCategory(ui);
        default:
            throw new MooMooException("Sorry I did not recognize that command.");

        }
    }

    private static Command parseAddCategory(Ui ui) {
        ui.showAddCategoryMessage();
        String categoryName = ui.readCommand();
        return new AddCategoryCommand(categoryName);
    }
}
