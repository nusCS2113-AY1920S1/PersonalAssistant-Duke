package moomoo;

import java.util.Scanner;

public class Parser {

    /**
     * Processes command from user input.
     *
     * @param command user's input
     * @return recognized command
     * @throws MooMooException user input is not a valid command
     */
    public static Command parseCommand(String command, Ui ui) throws MooMooException {
        Scanner scanner = new Scanner(command);
        String commandType = scanner.next();
        switch (commandType) {
        case ("bye"): return new ByeCommand();
        case ("add"): return parseAdd(scanner, ui);
        case ("categories"): return new ListCategoryCommand();
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

