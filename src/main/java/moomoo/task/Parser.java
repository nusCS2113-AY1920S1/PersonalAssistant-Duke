package moomoo.task;

import moomoo.command.AddCategoryCommand;
import moomoo.command.AddExpenditureCommand;
import moomoo.command.Command;
import moomoo.command.DeleteCategoryCommand;
import moomoo.command.EditBudgetCommand;
import moomoo.command.ExitCommand;
import moomoo.command.GraphCommand;
import moomoo.command.ListBudgetCommand;
import moomoo.command.ListCategoryCommand;
import moomoo.command.SavingsBudgetCommand;
import moomoo.command.ScheduleCommand;
import moomoo.command.SetBudgetCommand;
import moomoo.command.TotalCommand;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        case ("bye"):
            return new ExitCommand(true);
        case ("budget"):
            return parseBudget(scanner, ui);
        case ("categories"):
            return new ListCategoryCommand();
        case ("schedule"):
            return new ScheduleCommand(false, input);
        case ("add"):
            return parseAdd(scanner, ui);
        case ("delete"):
            return parseDelete(scanner, ui);
        case ("list"):
            return parseList(scanner, ui);
        case ("graph"):
            return new GraphCommand(input);
        case ("total"):
            return new TotalCommand();
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

    private static Command parseBudget(Scanner scanner, Ui ui) throws MooMooException {
        String input = scanner.next();
        switch (input) {
        case "set":
            return setBudget(scanner);
        case "edit":
            return editBudget(scanner);
        case "list":
            return listBudget(scanner);
        case "savings":
            return savingsBudget(scanner);
        default :
            throw new MooMooException("There is only edit/set/list/savings command under budget.");
        }
    }

    private static Command setBudget(Scanner scanner) throws MooMooException {
        String input = scanner.next();
        int count = 0;
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> budgets = new ArrayList<>();
        String inputCategory = "";

        while (true) {
            if (input.startsWith("c/") && count % 2 == 0) {
                inputCategory += input.substring(2).toLowerCase();
            } else if (input.startsWith("b/") && count % 2 != 0) {
                double budget = 0;
                try {
                    budget = Double.parseDouble(input.substring(2));
                    if (budget <= 0) {
                        throw new MooMooException("Please insert a number larger than 0 for the budget");
                    }
                } catch (NumberFormatException e) {
                    throw new MooMooException("Please insert a number larger than 0 for the budget");
                }
                categories.add(inputCategory);
                budgets.add(budget);
                inputCategory = "";
            } else {
                if (inputCategory != "") {
                    inputCategory += " " + input;
                } else {
                    throw new MooMooException("Please input in this format \"c/CATEGORY b/BUDGET\"");
                }
            }
            ++count;
            try {
                input = scanner.next();
            } catch (NoSuchElementException e) {
                break;
            }
        }

        if (categories.size() == 0 || budgets.size() == 0) {
            throw new MooMooException("You have entered the command wrongly. "
                    + "Please input in this format \"c/CATEGORY b/BUDGET\"");
        }

        return new SetBudgetCommand(false, categories, budgets);
    }

    private static Command editBudget(Scanner scanner) throws MooMooException {
        String input = scanner.next();
        int count = 0;
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> budgets = new ArrayList<>();
        String inputCategory = "";

        while (true) {
            if (input.startsWith("c/") && count % 2 == 0) {
                inputCategory += input.substring(2).toLowerCase();
            } else if (input.startsWith("b/") && count % 2 != 0) {
                double budget = 0;
                try {
                    budget = Double.parseDouble(input.substring(2));
                    if (budget <= 0) {
                        throw new MooMooException("Please insert a number larger than 0 for the budget");
                    }
                } catch (NumberFormatException e) {
                    throw new MooMooException("Please insert a number larger than 0 for the budget");
                }
                categories.add(inputCategory);
                budgets.add(budget);
                inputCategory = "";
            } else {
                if (inputCategory != "") {
                    inputCategory += " " + input;
                } else {
                    throw new MooMooException("Please input in this format \"c/CATEGORY b/BUDGET\"");
                }
            }
            ++count;
            try {
                input = scanner.next();
            } catch (NoSuchElementException e) {
                break;
            }
        }

        if (categories.size() == 0 || budgets.size() == 0) {
            throw new MooMooException("You have entered the command wrongly. "
                    + "Please input in this format \"c/CATEGORY b/BUDGET\"");
        }

        return new EditBudgetCommand(false, categories, budgets);
    }

    private static Command listBudget(Scanner scanner) throws MooMooException {
        String input = scanner.next();
        ArrayList<String> categories = new ArrayList<>();
        String inputCategory = "";

        while (true) {
            if (input.startsWith("c/")) {
                if (inputCategory != "") {
                    categories.add(inputCategory);
                    inputCategory = "";
                }
                inputCategory += input.substring(2).toLowerCase();
            } else {
                if (inputCategory != "") {
                    inputCategory += " " + input;
                } else {
                    throw new MooMooException("Please input in this format \"c/CATEGORY\"");
                }
            }
            try {
                input = scanner.next();
            } catch (NoSuchElementException e) {
                if (inputCategory != "") {
                    categories.add(inputCategory);
                }
                break;
            }
        }
        return new ListBudgetCommand(false, categories);
    }

    private static Command savingsBudget(Scanner scanner) throws MooMooException {
        String input = scanner.next();
        ArrayList<String> categories = new ArrayList<>();
        String startMonth = "";
        String endMonth = "";
        String inputCategory = "";

        while (true) {
            if (input.startsWith("c/")) {
                if (inputCategory != "") {
                    categories.add(inputCategory);
                    inputCategory = "";
                }
                inputCategory += input.substring(2).toLowerCase();
            } else if (input.startsWith("s/")) {
                if (inputCategory != "") {
                    categories.add(inputCategory);
                    inputCategory = "";
                }
                if (startMonth != "") {
                    throw new MooMooException("Please only set 1 starting period.");
                }
                startMonth = input.substring(2);
            } else if (input.startsWith("e/")) {
                if (endMonth != "") {
                    throw new MooMooException("Please only set 1 starting period.");
                }
                endMonth = input.substring(2);

            } else {
                if (inputCategory != "") {
                    inputCategory += " " + input;
                }
                throw new MooMooException("Please input in this format \"c/CATEGORY s/STARTMONTHYEAR e/ENDMONTHYEAR\"");
            }

            try {
                input = scanner.next();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        if (startMonth == "") {
            throw new MooMooException("Please set a start month and year in this format \"s/01/2019\"");
        }

        LocalDate startDate = parseMonth(startMonth);
        LocalDate endDate = parseMonth(endMonth);

        if (startDate == null) {
            throw new MooMooException("Please set a start month and year in this format \"s/01/2019\"");
        }

        if (!"".equals(endMonth) && endDate == null) {
            throw new MooMooException("Please set a end month and year in this format \"e/01/2019\"");
        }

        if (categories.size() == 0) {
            throw new MooMooException("You have entered the command wrongly. "
                    + "Please input in this format \"c/CATEGORY b/BUDGET\"");
        }

        return new SavingsBudgetCommand(false, categories, startDate, endDate);
    }

    private static LocalDate parseMonth(String inputDate) {
        try {
            String fullDate = "01/" + inputDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return java.time.LocalDate.parse(fullDate, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}
