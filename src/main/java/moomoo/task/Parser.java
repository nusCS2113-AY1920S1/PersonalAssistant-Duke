package moomoo.task;

import moomoo.command.AddExpenditureCommand;
import moomoo.command.AddCategoryCommand;
import moomoo.command.BudgetCommand;
import moomoo.command.Command;
import moomoo.command.DeleteCategoryCommand;
import moomoo.command.ExitCommand;
import moomoo.command.GraphCommand;
import moomoo.command.ListCategoryCommand;
import moomoo.command.TotalCommand;

import java.util.NoSuchElementException;
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
        case ("add"): return parseAdd(scanner, ui);
        case ("delete"): return parseDelete(scanner, ui);
        case ("list"): return parseList(scanner, ui);
        case ("graph"): return new GraphCommand(input);
        case ("total") : return new TotalCommand();
        default: throw new MooMooException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static Command parseList(Scanner scanner, Ui ui) throws MooMooException {
        String input = parseInput(scanner, ui, "list");
        switch (input) {
        case ("category"): return new ListCategoryCommand();
        default: throw new MooMooException("Sorry I did not recognize that command.");
        }
    }

    private static Command parseDelete(Scanner scanner, Ui ui) throws MooMooException {
        String input = parseInput(scanner, ui, "delete");
        switch (input) {
        case ("category"): return new DeleteCategoryCommand();
        default: throw new MooMooException("Sorry I did not recognize that command.");
        }
    }

    private static Command parseAdd(Scanner scanner, Ui ui) throws MooMooException {
        //String input = parseInput(scanner, ui, "add");
        //switch (input) {
        switch (scanner.next()) {
        case ("category"): return new AddCategoryCommand();
        case ("expenditure"): return parseAddExpenditure(ui);
        default:
            throw new MooMooException("Sorry I did not recognize that command.");

        }
    }

    private static Command parseAddExpenditure(Ui ui) {
        ui.showAddExpenditureMessage();
        String input = ui.readCommand();
        String[] parts = input.split("-");
        String expenditureName = parts[0];
        String amount = parts[1];

        return new AddExpenditureCommand(false, amount, expenditureName);
    }

    private static String parseInput(Scanner scanner, Ui ui, String text) {
        String input;
        try {
            input = scanner.next();
        } catch (NoSuchElementException e) {
            ui.showAddMessage(text);
            input = ui.readCommand();
        }
        return input;
    }
}
