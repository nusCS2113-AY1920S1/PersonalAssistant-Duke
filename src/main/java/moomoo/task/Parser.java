package moomoo.task;

import moomoo.command.AddCategoryCommand;
import moomoo.command.AddExpenditureCommand;
import moomoo.command.Command;
import moomoo.command.DeleteCategoryCommand;
import moomoo.command.EditBudgetCommand;
import moomoo.command.ExitCommand;
import moomoo.command.GraphCategoryCommand;
import moomoo.command.GraphTotalCommand;
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
     *
     * @param input String given by the user
     * @param ui    MooMoo's ui
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
            return parseBudget(scanner);
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
            return parseGraph(scanner);
        case ("total"):
            return new TotalCommand();
        default:
            throw new MooMooException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
    
    private static Command parseGraph(Scanner scanner) throws MooMooException {
        String input;
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Try adding c/[CATEGORY] d/[MONTH] or \"total\"!");
        }
        switch (input) {
        case "total":
            return new GraphTotalCommand();
        default:
            String month;
            try {
                month = scanner.next();
                if (0 < Integer.parseInt(month) && Integer.parseInt(month) < 13) {
                    return new GraphCategoryCommand(input, Integer.parseInt(month));
                } else {
                    throw new MooMooException("Month must be from 1 to 12 inclusive!");
                }
            } catch (Exception e) {
                throw new MooMooException("Try adding c/[CATEGORY] d/[MONTH] or \"total\"!");
            }
            
        }
    }
    
    private static Command parseList(Scanner scanner, Ui ui) throws MooMooException {
        String text = "What do you wish to list?" + "\n(c/) category" + "\n(n/) expenditure";
        String input = parseInput(scanner, ui, text);
        if (input.equals("c/")) {
            return new ListCategoryCommand();
        }
        throw new MooMooException("Sorry I did not recognize that command.");
    }
    
    private static Command parseDelete(Scanner scanner, Ui ui) throws MooMooException {
        String text = "What do you wish to delete?" + "\n(c/) category" + "\n(n/) expenditure";
        String input = parseInput(scanner, ui, text);
        if (input.startsWith("c/")) {
            String categoryNumber = removeSuffix(input);
            try {
                return new DeleteCategoryCommand(Integer.parseInt(categoryNumber));
            } catch (NumberFormatException e) {
                throw new MooMooException("Try a command like delete c/[Category Number]");
            }
        }
        throw new MooMooException("Sorry I did not recognize that command.");
    }
    
    private static Command parseAdd(Scanner scanner, Ui ui) throws MooMooException {
        String text = "What do you wish to add?" + "\n(c/) category" + "\n(n/) expenditure";
        String input = parseInput(scanner, ui, text);
        if (input.startsWith("c/")) {
            String categoryName = removeSuffix(input);
            if (!categoryName.isBlank()) {
                return new AddCategoryCommand(categoryName);
            }
            throw new MooMooException("Try a command like add c/[Category Name]");
        } else {
            String categoryName = "";
            String expenditureName = "";
            Double amount = 0.0;
            LocalDate date = LocalDate.now();
            String[] tokens = input.split("/|\\s+");
            int tokenCount = tokens.length;
            for (int i = 0; i < tokenCount; i++) {
                if (tokens[i].equals("c")) {
                    categoryName = tokens[i + 1];
                } else if (tokens[i].equals("n")) {
                    expenditureName = tokens[i + 1];
                } else if (tokens[i].equals("a")) {
                    amount = Double.parseDouble(tokens[i + 1]);
                } else if (tokens[i].equals("d")) {
                    date = LocalDate.parse(tokens[i + 1]);
                }
            }

            /*
            for (int j = 0; j < tokenCount; j++) { //for testing
                System.out.println("Split Output: "+ tokens[j]); //for testing
            }
            System.out.println(categoryName);  //for testing
            System.out.println(expenditureName);  //for testing
            System.out.println(amount);  //for testing
            System.out.println(date);  //for testing
             */

            if (!categoryName.isBlank() && !expenditureName.isBlank() && !amount.equals(0.0)) {
                return new AddExpenditureCommand(expenditureName, amount, date, categoryName);
            }
            throw new MooMooException("Try a command like add n/[Expenditure Name] a/[Amount] d/[Date] "
                    + "c/[Category Name]");
        }
    }

    private static String removeSuffix(String noSpaceInput) throws MooMooException {
        String categoryName;
        try {
            categoryName = noSpaceInput.substring(2).trim();
        } catch (NoSuchElementException e) {
            throw new MooMooException("Please enter a category after the /");
        }
        return categoryName;
    }

    /*
    private static Command parseAddExpenditure(Ui ui) {
        ui.showAddExpenditureMessage();
        String input = ui.readCommand();
        String[] parts = input.split("-");
        String expenditureName = parts[0];
        String amount = parts[1];
        
        return new AddExpenditureCommand(false, amount, expenditureName);
    }
*/
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
    
    private static Command parseBudget(Scanner scanner) throws MooMooException {
        String input;
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("There is only edit/set/list/savings command under budget.");
        }
        switch (input) {
        case "set":
            return setBudget(scanner);
        case "edit":
            return editBudget(scanner);
        case "list":
            return listBudget(scanner);
        case "savings":
            return savingsBudget(scanner);
        default:
            throw new MooMooException("There is only edit/set/list/savings command under budget.");
        }
    }
    
    private static Command setBudget(Scanner scanner) throws MooMooException {
        String input;
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Please input in this format \"c/CATEGORY b/BUDGET\"");
        }
        int count = 0;
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> budgets = new ArrayList<>();
        String inputCategory = "";
        
        while (!"".equals(input)) {
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
        String input;
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Please input in this format \"c/CATEGORY b/BUDGET\"");
        }
        int count = 0;
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> budgets = new ArrayList<>();
        String inputCategory = "";
        
        while (!"".equals(input)) {
            if (input.startsWith("c/") && count % 2 == 0) {
                inputCategory += input.substring(2).toLowerCase();
            } else if (input.startsWith("b/") && count % 2 != 0) {
                double budget;
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
        String input = "";
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Please input in this format \"c/CATEGORY\"");
        }
        ArrayList<String> categories = new ArrayList<>();
        String inputCategory = "";
        
        while (!"".equals(input)) {
            if (input.startsWith("c/")) {
                if (inputCategory != "") {
                    categories.add(inputCategory);
                    inputCategory = "";
                }
                inputCategory += input.substring(2).toLowerCase();
            } else {
                if (!"".equals(inputCategory)) {
                    inputCategory += " " + input;
                } else {
                    throw new MooMooException("Please input in this format \"c/CATEGORY\"");
                }
            }
            try {
                input = scanner.next();
            } catch (NoSuchElementException e) {
                if (!"".equals(inputCategory)) {
                    categories.add(inputCategory);
                }
                break;
            }
        }
        return new ListBudgetCommand(false, categories);
    }
    
    private static Command savingsBudget(Scanner scanner) throws MooMooException {
        String input;
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Please input in this format \"c/CATEGORY s/STARTMONTHYEAR e/ENDMONTHYEAR\"");
        }
        
        ArrayList<String> categories = new ArrayList<>();
        String startMonth = "";
        String endMonth = "";
        String inputCategory = "";
        
        while (!"".equals(input)) {
            if (input.startsWith("c/")) {
                if (!"".equals(inputCategory)) {
                    categories.add(inputCategory);
                    inputCategory = "";
                }
                inputCategory += input.substring(2).toLowerCase();
            } else if (input.startsWith("s/")) {
                if (!"".equals(inputCategory)) {
                    categories.add(inputCategory);
                    inputCategory = "";
                }
                if (!"".equals(startMonth)) {
                    throw new MooMooException("Please only set 1 starting period.");
                }
                startMonth = input.substring(2);
            } else if (input.startsWith("e/")) {
                if (!"".equals(endMonth)) {
                    throw new MooMooException("Please only set 1 ending period.");
                }
                endMonth = input.substring(2);
                
            } else {
                if (!"".equals(inputCategory)) {
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
        if ("".equals(startMonth)) {
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
                    + "Please input in this format \"c/CATEGORY s/STARTMONTHYEAR e/ENDMONTHYEAR\"");
        }
        
        return new SavingsBudgetCommand(false, categories, startDate, endDate);
    }
    
    private static LocalDate parseMonth(String inputDate) {
        try {
            String fullDate = "01/" + inputDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(fullDate, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}
