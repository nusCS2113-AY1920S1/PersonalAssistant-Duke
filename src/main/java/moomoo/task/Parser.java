package moomoo.task;

import moomoo.command.Command;
import moomoo.command.EditBudgetCommand;
import moomoo.command.ExitCommand;
import moomoo.command.GraphCategoryCommand;
import moomoo.command.GraphTotalCommand;
import moomoo.command.HelpCommand;
import moomoo.command.ListBudgetCommand;
import moomoo.command.MainDisplayCommand;
import moomoo.command.MooCommand;
import moomoo.command.SavingsBudgetCommand;
import moomoo.command.ScheduleCommand;
import moomoo.command.SetBudgetCommand;
import moomoo.command.TotalCommand;
import moomoo.command.category.AddCategoryCommand;
import moomoo.command.category.AddExpenditureCommand;
import moomoo.command.category.DeleteCategoryCommand;
import moomoo.command.category.ListCategoryCommand;
import moomoo.task.category.CategoryParser;

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
        String commandType;
        try {
            commandType = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        switch (commandType) {
        case ("bye"):
        case ("exit"):
            return new ExitCommand(true);
        case ("budget"):
            return parseBudget(scanner);
        case ("schedule"):
            return new ScheduleCommand(false, input);
        case ("add"):
            return parseAdd(scanner, ui);
        case ("delete"):
            return parseDelete(scanner, ui);
        case ("sort"):
            return CategoryParser.parseSort(scanner, ui);
        case ("list"):
            return new ListCategoryCommand();
        case ("graph"):
            return parseGraph(scanner);
        case ("total"):
            return new TotalCommand();
        case ("help"):
            return new HelpCommand();
        case ("moo"):
            return new MooCommand();
        case ("view"):
            return parseView(scanner, ui);
        default:
            throw new MooMooException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
    
    private static Command parseGraph(Scanner scanner) throws MooMooException {
        String input;
        String inputCategory = "";
        int inputMonth = LocalDate.now().getMonthValue();
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Try adding c/[CATEGORY] d/[MONTH] or \"total\"!");
        }
        
        try {
            if (input.equals("total")) {
                return new GraphTotalCommand();
            } else if (input.startsWith("c/")) {
                inputCategory = input.substring(2);
            } else if (input.startsWith("m/")) {
                inputMonth = Integer.parseInt(input.substring(2));
            }
            if (scanner.hasNext()) {
                input = scanner.next();
            }
            if (input.startsWith("c/")) {
                inputCategory = input.substring(2);
            } else if (input.startsWith("m/")) {
                inputMonth = Integer.parseInt(input.substring(2));
            }
        } catch (Exception e) {
            throw new MooMooException("Please enter a number (from 1 to 12) for the month!!! MOOOOO!!!!");
        }
        if (inputCategory.equals("")) {
            throw new MooMooException("Please add a CATEGORY!!! MoOoOoOoO");
        }
        else if (0 < inputMonth && inputMonth < 13) {
            return new GraphCategoryCommand(inputCategory,
                    inputMonth,
                    LocalDate.now().getYear());
        } else {
            throw new MooMooException("Please enter a number (from 1 to 12) for the month!!! MOOOOO!!!!");
        }
    }


//        String input;
//        try {
//            input = scanner.next();
//        } catch (Exception e) {
//            throw new MooMooException("Try adding c/[CATEGORY] d/[MONTH] or \"total\"!");
//        }
//
//        if (input.equals("total")) {
//            return new GraphTotalCommand();
//        } else if (input.startsWith("c/")) {
//            String inputCategory = input.substring(2);
//            if (scanner.hasNext()) {
//                input = scanner.next();
//                if (input.startsWith("m/")) {
//                    try {
//                        String inputDate = input.substring(2);
//                        if (!inputDate.equals("") && 0 < Integer.parseInt(inputDate)
//                                && Integer.parseInt(inputDate) < 13) {
//                            return new GraphCategoryCommand(inputCategory,
//                                    Integer.parseInt(inputDate),
//                                    LocalDate.now().getYear());
//                        } else {
//                            throw new MooMooException("Month must be from 1 to 12 (inclusive)!");
//                        }
//                    } catch (Exception e) {
//                        throw new MooMooException("Month must be from 1 to 12 (inclusive)!");
//                    }
//                } else {
//                    throw new MooMooException("Please enter in this format: d/[MONTH]");
//                }
//            } else {
//                return new GraphCategoryCommand(inputCategory,
//                        LocalDate.now().getMonthValue(),
//                        LocalDate.now().getYear());
//            }
//
//        } else {
//            throw new MooMooException("Please enter in this format: c/[CATEGORY]");
//        }
//
    
    
    private static Command parseDelete(Scanner scanner, Ui ui) throws MooMooException {
        String text = "What do you wish to delete?" + "\n(c/) category" + "\n(n/) expenditure";
        String input = parseInput(scanner, ui, text);
        if (input.startsWith("c/")) {
            String categoryName = removeSuffix(input);
            try {
                return new DeleteCategoryCommand(categoryName);
            } catch (NumberFormatException e) {
                throw new MooMooException("Try a command like delete c/[Category Number]");
            }
        }
        throw new MooMooException("Sorry I did not recognize that command.");
    }
    
    private static Command parseView(Scanner scanner, Ui ui) throws MooMooException {
        String text = "Which month's summary do you wish to view?" + "(m/) month" + "(y/) year";
        String input = parseInput(scanner, ui, text);
        LocalDate now = LocalDate.now();
        if (input.startsWith("m/") || input.startsWith("y/")) {
            int month = now.getMonthValue();
            int year = now.getYear();
            String[] tokens = input.split("/|\\s+");
            int tokenCount = tokens.length;
            for (int i = 0; i < tokenCount; i++) {
                if (tokens[i].equals("m")) {
                    month = Integer.parseInt(tokens[i + 1]);
                } else if (tokens[i].equals("y")) {
                    year = Integer.parseInt(tokens[i + 1]);
                }
            }
            if (month != 0 && year != 0) {
                return new MainDisplayCommand(month, year);
            }
        } else if (input.equals("current")) {
            int month = now.getMonth().getValue();
            int year = now.getYear();
            return new MainDisplayCommand(month, year);
        } else if (input.equals("all")) {
            int month = 0;
            int year = 0;
            return new MainDisplayCommand(month, year);
        }
        throw new MooMooException("Sorry, but MooMoo no recognize that command :(");
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
            return setOrEditBudget(scanner, false);
        case "edit":
            return setOrEditBudget(scanner, true);
        case "list":
            return listBudget(scanner);
        case "savings":
            return savingsBudget(scanner);
        default:
            throw new MooMooException("There is only edit/set/list/savings command under budget.");
        }
    }
    
    private static Command setOrEditBudget(Scanner scanner, boolean isEdit) throws MooMooException {
        String input;
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Please input in this format \"c/CATEGORY b/BUDGET\"");
        }
        boolean isNewCategory = false;
        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Double> budgets = new ArrayList<>();
        String inputCategory = "";
        
        while (!"".equals(input)) {
            if (input.startsWith("c/")) {
                if (isNewCategory) {
                    if (!"".equals(inputCategory)) {
                        categories.add(inputCategory);
                    }
                    inputCategory = input.substring(2).toLowerCase();
                    isNewCategory = false;
                    continue;
                }
                inputCategory = input.substring(2).toLowerCase();
                isNewCategory = true;
            } else if (input.startsWith("b/")) {
                double budget = 0;
                if ("".equals(inputCategory)) {
                    throw new MooMooException("Please input in this format \"c/CATEGORY b/BUDGET\"");
                }
                
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
                if (categories.size() > budgets.size()) {
                    budgets.add(budget);
                }
                inputCategory = "";
            } else {
                if (!"".equals(inputCategory)) {
                    inputCategory += " " + input;
                } else {
                    throw new MooMooException("Please input in this format \"c/CATEGORY b/BUDGET\"");
                }
            }
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
        if (isEdit) {
            return new EditBudgetCommand(false, categories, budgets);
        } else {
            return new SetBudgetCommand(false, categories, budgets);
            
        }
    }
    
    private static Command listBudget(Scanner scanner) throws MooMooException {
        ArrayList<String> categories = new ArrayList<>();
        
        String input;
        try {
            input = scanner.next();
        } catch (Exception e) {
            return new ListBudgetCommand(false, categories);
            
        }
        String inputCategory = "";
        
        while (!"".equals(input)) {
            if (input.startsWith("c/")) {
                if (!"".equals(inputCategory)) {
                    categories.add(inputCategory);
                    inputCategory = "";
                }
                inputCategory = input.substring(2).toLowerCase();
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
            throw new MooMooException("Please input in this format \"c/CATEGORY s/STARTMONTHYEAR e/ENDMONTHYEAR\"\n");
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
                    throw new MooMooException("Please only set 1 starting period.\n");
                }
                startMonth = input.substring(2);
            } else if (input.startsWith("e/")) {
                if (!"".equals(endMonth)) {
                    throw new MooMooException("Please only set 1 ending period.\n");
                }
                endMonth = input.substring(2);
                
            } else {
                if (!"".equals(inputCategory)) {
                    inputCategory += " " + input;
                }
                throw new MooMooException("Please input in this format \"c/CATEGORY "
                        + "s/STARTMONTHYEAR e/ENDMONTHYEAR\"\n");
            }
            
            try {
                input = scanner.next();
            } catch (NoSuchElementException e) {
                break;
            }
        }
        if ("".equals(startMonth)) {
            throw new MooMooException("Please set a start month and year in this format \"s/01/2019\"\n");
        }
        
        LocalDate startDate = parseMonth(startMonth);
        LocalDate endDate = parseMonth(endMonth);
        
        if (startDate == null) {
            throw new MooMooException("Please set a start month and year in this format \"s/01/2019\"\n");
        }
        
        if (!"".equals(endMonth) && endDate == null) {
            throw new MooMooException("Please set an end month and year in this format \"e/01/2019\"\n");
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
