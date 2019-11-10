package moomoo.feature.parser;

import moomoo.command.Command;
import moomoo.command.category.AddCategoryCommand;
import moomoo.command.category.DeleteCategoryCommand;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;

import java.util.Scanner;

class CategoryParser extends Parser {

    /**
     * Parses which command to return based on the user's input.
     * Valid inputs are add, edit, or delete.
     * @param scanner user input
     * @return an add, edit, or delete command.
     * @throws MooMooException if user's input does not match any of valid commands
     */
    static Command parse(Scanner scanner) throws MooMooException {
        String commandType;
        if (scanner.hasNext()) {
            commandType = scanner.next();
        } else {
            Ui.setOutput("Would you like to add, edit, or delete a category?\n"
                    + "Try entering one of these commands:\n"
                    + "category add [CATEGORY NAME]\n"
                    + "category edit [OLD CATEGORY NAME] [NEW CATEGORY NAME]\n"
                    + "category delete [CATEGORY NAME or CATEGORY INDEX NUMBER]");
            throw new MooMooException("");
        }
        switch (commandType) {
        case ("add"): return parseAdd(scanner);
        case ("delete"): return parseDelete(scanner);
        default: throw new MooMooException("Oops, recognized commands are <add>, <edit>, and <delete>.");
        }
    }

    private static Command parseAdd(Scanner scanner) {
        String text = "What category would you like to add?";
        String categoryName = parseInput(scanner, text);
        return new AddCategoryCommand(categoryName);
    }

    private static Command parseDelete(Scanner scanner) {
        String text = "What category do you wish to delete?";
        String categoryName = parseInput(scanner, text);
        try {
            int categoryIndex = Integer.parseInt(categoryName) - 1;
            return new DeleteCategoryCommand(categoryIndex);
        } catch (NumberFormatException e) {
            return new DeleteCategoryCommand(categoryName);
        }
    }
}
