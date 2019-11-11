package moomoo.feature.parser;

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
import moomoo.command.category.ListCategoryCommand;
import moomoo.feature.MooMooException;
import moomoo.feature.Ui;

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
     * @return The command object corresponding to the user input
     * @throws MooMooException Thrown when an invalid input is given
     */
    public static Command parse(String input) throws MooMooException {
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
        case ("category"):
            return CategoryParser.parse(scanner);
        case ("list"):
            return new ListCategoryCommand();
        case ("graph"):
            return parseGraph(scanner);
        case ("help"):
            return new HelpCommand();
        case ("moo"):
            return new MooCommand();
        case ("view"):
            return parseView(scanner);
        default:
            return ExpenditureParser.parse(commandType, scanner);
        }
    }
    
    static String parseInput(Scanner scanner, String text) {
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim().toLowerCase();
        } else {
            Ui.showPrompt(text);
            return Ui.readCommand().trim().toLowerCase();
        }
    }
    
    private static Command parseGraph(Scanner scanner) throws MooMooException {
        String input;
        String inputCategory = "";
        int inputMonth = LocalDate.now().getMonthValue();
        int inputYear = LocalDate.now().getYear();
        try {
            input = scanner.next();
        } catch (Exception e) {
            throw new MooMooException("Try adding c/[CATEGORY] m/[MONTH] y/[YEAR] or \"total\"!\n"
                    + "*month and year are optional!");
        }
        
        try {
            if (input.equals("total")) {
                return new GraphTotalCommand();
            } else if (input.startsWith("c/")) {
                inputCategory = input.substring(2);
            } else if (input.startsWith("m/")) {
                inputMonth = Integer.parseInt(input.substring(2));
            } else if (input.startsWith("y/")) {
                inputYear = Integer.parseInt(input.substring(2));
            }
            if (scanner.hasNext()) {
                input = scanner.next();
            }
            if (input.startsWith("c/")) {
                inputCategory = input.substring(2);
            } else if (input.startsWith("m/")) {
                inputMonth = Integer.parseInt(input.substring(2));
            } else if (input.startsWith("y/")) {
                inputYear = Integer.parseInt(input.substring(2));
            }
            if (scanner.hasNext()) {
                input = scanner.next();
            }
            if (input.startsWith("c/")) {
                inputCategory = input.substring(2);
            } else if (input.startsWith("m/")) {
                inputMonth = Integer.parseInt(input.substring(2));
            } else if (input.startsWith("y/")) {
                inputYear = Integer.parseInt(input.substring(2));
            }
        } catch (Exception e) {
            throw new MooMooException("Please enter valid input for the parameters!! MOOOOO!!!!");
        }
        
        
        if (inputCategory.equals("")) {
            throw new MooMooException("Please add a CATEGORY!!! MoOoOoOoO");
        } else if (0 < inputMonth && inputMonth < 13) {
            return new GraphCategoryCommand(inputCategory,
                    inputMonth,
                    inputYear);
        } else {
            throw new MooMooException("Please enter a number (from 1 to 12) for the month!!! MOOOOO!!!!");
        }
    }
    
    
    private static Command parseView(Scanner scanner) throws MooMooException {
        String text = "Which month's summary do you wish to view?\n"
                + "1.(m/) month" + " (y/) year\n"
                + "2.current\n"
                + "3.all\n";
        String input = parseInput(scanner, text);
        LocalDate now = LocalDate.now();
        if (input.startsWith("m/") || input.startsWith("y/")) {
            int month = now.getMonthValue();
            int year = now.getYear();
            String[] tokens = input.split("/|\\s+");
            int tokenCount = tokens.length;
            for (int i = 0; i < tokenCount; i++) {
                if (tokens[i].equals("m")) {
                    try {
                        month = Integer.parseInt(tokens[i + 1]);
                    } catch (Exception e) {
                        throw new MooMooException("Please enter a valid month (from 1 to 12)!!! MOOOOO!!!!");
                    }
                } else if (tokens[i].equals("y")) {
                    try {
                        year = Integer.parseInt(tokens[i + 1]);
                    } catch (Exception e) {
                        throw new MooMooException("Please enter a valid year!!! MOOOOOO!!!!!");
                    }
                }
            }
            if ((month > 0 && month <= 12) && (year > 0 && year <= 10000)) {
                return new MainDisplayCommand(month, year);
            } else {
                throw new MooMooException("Please enter a valid month (from 1 to 12) and a valid year!!! MOOOOO!!!!");
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
