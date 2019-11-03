package moomoo.task.category;

import moomoo.command.Command;
import moomoo.command.category.SortCategoryCommand;
import moomoo.task.Ui;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CategoryParser {

    /**
     * Parses how the user wants the list to be sorted.
     * @param scanner user input
     * @param ui ui
     * @return command to sort the category list
     */
    public static Command parseSort(Scanner scanner, Ui ui) {
        String text = "parse sort error";
        String sortType = parseInput(scanner, ui, text);
        return new SortCategoryCommand(sortType);
    }

    private static String parseInput(Scanner scanner, Ui ui, String text) {
        String input;
        try {
            input = scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            ui.showInputPrompt(text);
            input = ui.readCommand();
        }
        return input;
    }
}
