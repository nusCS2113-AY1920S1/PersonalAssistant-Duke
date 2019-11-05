package moomoo.feature.parser;

import moomoo.command.Command;
import moomoo.command.category.AddExpenditureCommand;
import moomoo.command.category.DeleteExpenditureCommand;
import moomoo.command.category.SortCategoryCommand;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

class ExpenditureParser extends Parser {

    static Command parse(String commandType, Scanner scanner) throws MooMooException {
        switch (commandType) {
        //case ("expenditure add"):
        case ("add"): return parseAdd(scanner);
        //case ("expenditure edit"):
        //case ("edit"):
        //case ("expenditure delete"):
        case ("delete"): return parseDelete(scanner);
        case ("sort"): return parseSort(scanner);
        default: throw new MooMooException("OOPS!!! I'm sorry, but I don't know what that means :-(\n"
                        + "Use the <help> command to see a list of commands.");
        }
    }

    private static Command parseAdd(Scanner scanner) throws MooMooException {
        String text = "What expenditure do you wish to add? Please enter:"
                + "n/[NAME] c/[CATEGORY] a/[AMOUNT] (optional: d/[YYYY-MM-DD])";
        String input = parseInput(scanner, text);

        String categoryName = "";
        String expenditureName = "";
        String amountString = "";
        String dateString = "";
        double amount = 0;
        LocalDate date;
        String[] tokens = input.split("/|\\s+");
        int tokenCount = tokens.length;
        for (int i = 0; i < tokenCount; i++) {
            if (tokens[i].equals("c")) {
                categoryName = tokens[i + 1];
            } else if (tokens[i].equals("n")) {
                expenditureName = tokens[i + 1];
            } else if (tokens[i].equals("a")) {
                amountString = tokens[i + 1];
            } else if (tokens[i].equals("d")) {
                dateString = tokens[i + 1];
            }
        }

        if (categoryName.isBlank()) {
            throw new MooMooException("Oops, you forgot to enter a category.");
        }
        if (expenditureName.isBlank()) {
            throw new MooMooException("Oops, you forgot to enter a name.");
        }
        try {
            amount = Double.parseDouble(amountString);
            date = LocalDate.parse(dateString);
        } catch (NumberFormatException e) {
            throw new MooMooException("Oops, the amount you entered was not recognized,"
                    + "please use an double value e.g. 9.90.");
        } catch (DateTimeException e) {
            date = LocalDate.now();
            Ui.showPrompt("Oops, the date you entered was not recognized and has been set to today's date,"
                    + "you can still edit the date using the command <edit expenditure> with the format YYYY-MM-DD.");
        }
        if (amount > 0) {
            return new AddExpenditureCommand(expenditureName, amount, date, categoryName);
        }
        throw new MooMooException("Oops, the amount you entered was less than or equal to zero.");
    }

    private static Command parseDelete(Scanner scanner) throws MooMooException {
        String text = "What expenditure do you wish to add? Please enter:"
                + "delete i/[INDEX] c/[CATEGORY]";
        String input = parseInput(scanner, text);

        String categoryName = "";
        String indexString = "";
        String[] tokens = input.split("/|\\s+");
        int tokenCount = tokens.length;
        for (int i = 0; i < tokenCount; i++) {
            if (tokens[i].equals("c")) {
                categoryName = tokens[i + 1];
            } else if (tokens[i].equals("i")) {
                indexString = tokens[i + 1];
            }
        }
        try {
            int index = Integer.parseInt(indexString) - 1;
            return new DeleteExpenditureCommand(index, categoryName);
        } catch (NumberFormatException e) {
            throw new MooMooException("Please use an integer value for the index.");
        }
    }

    private static Command parseSort(Scanner scanner) {
        String text = "parse sort error";
        String sortType = parseInput(scanner, text);
        return new SortCategoryCommand(sortType);
    }
}
