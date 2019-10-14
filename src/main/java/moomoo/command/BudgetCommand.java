package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.TransactionList;
import moomoo.task.Ui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the command to manage budget.
 */
public class BudgetCommand extends Command {
    private DecimalFormat df;

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     *
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input  Input given by the user
     */
    public BudgetCommand(boolean isExit, String input) {
        super(isExit, input);
        df = new DecimalFormat("#.00");
    }

    @Override
    public void execute(Budget budget, CategoryList catList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        try {
            if (input.length() == 6) {
                manageBudgetPrompt(budget, catList, transList, ui, storage);
            } else {
                manageBudget(budget, catList, transList, ui, storage);
            }
        } catch (MooMooException e) {
            throw new MooMooException(e.getMessage());
        }
    }

    /**
     * Parses String input into LocalDate object for parsing of dates.
     *
     * @param inputDate String input with format "01/2019"
     * @return LocalDate object if valid input else null
     */
    private LocalDate parseMonth(String inputDate) {
        try {
            inputDate = "01/" + inputDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate parsedDate = LocalDate.parse(inputDate, formatter);
            return parsedDate;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Runs necessary budgets command for one line input.
     *
     * @param budget    Budget object containing the budget.
     * @param catList   CategoryList object containing the categories
     * @param transList TransactionList object containing transactions within each category.
     * @param ui        Ui object for interaction with user interface.
     * @param storage   Storage object for interaction with filesystem.
     * @throws MooMooException Thrown when error such as invalid input occurs
     */
    private void manageBudget(Budget budget, CategoryList catList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        input = input.substring(7);
        String outputValue = "";
        if (input.startsWith("set ")) {
            input = input.substring(4);
            String[] splitInput = input.split(" ");
            String category = "";
            double addedBudget = 0;

            for (int i = 0; i < splitInput.length; ++i) {
                if (i % 2 == 0) {
                    if (splitInput[i].startsWith("c/")) {
                        category = splitInput[i].substring(2);
                        if (!inCategoryList(category, catList)) { //check if category exists.
                            throw new MooMooException(category + " does not exist. Please create it first.");
                        }
                    } else {
                        throw new MooMooException("Please place the category before the budget "
                                + "and use c/CATEGORY to set the category.\n");
                    }
                } else {
                    if (splitInput[i].startsWith("b/")) {
                        addedBudget = Double.parseDouble(splitInput[i].substring(2));
                        if (budget.getBudgetFromCategory(category) != 0) {
                            throw new MooMooException("The budget for " + category + " has already been set. "
                                    + "Please edit it using budget edit.");
                        }
                        budget.addNewBudget(category, addedBudget);
                        outputValue += "You have set $" + df.format(addedBudget)
                                + " as the budget for " + category + "\n";
                    } else {
                        throw new MooMooException("Please place the category before the budget "
                                + "and use b/BUDGET to set the budget.\n");
                    }
                }
            }
            ui.setOutput(outputValue.substring(0, outputValue.length() - 1));
            storage.saveBudgetToFile(budget);
        } else if (input.startsWith("edit ")) {
            input = input.substring(5);
            String[] splitInput = input.split(" ");
            String category = "";
            double addedBudget = 0;

            for (int i = 0; i < splitInput.length; ++i) {
                if (i % 2 == 0) {
                    if (splitInput[i].startsWith("c/")) {
                        category = splitInput[i].substring(2);
                        if (!inCategoryList(category, catList)) { //check if category exists.
                            throw new MooMooException("The " + category + " does not exist. Please create it first.");
                        }
                        if (budget.getBudgetFromCategory(category) == 0) {
                            throw new MooMooException("The budget for " + category + " does not exist."
                                    + "Please set it using budget set.");
                        }

                    } else {
                        throw new MooMooException("Please place the category before the budget and "
                                + "use c/CATEGORY to set the category.");
                    }
                } else {
                    if (splitInput[i].startsWith("b/")) {
                        try {
                            addedBudget = Double.parseDouble(splitInput[i].substring(2));
                            if (addedBudget <= 0) {
                                throw new MooMooException("The budget to set for " + category + " is invalid. "
                                        + "Please type in a positive number greater than 0");
                            }
                        } catch (NumberFormatException e) {
                            throw new MooMooException("The budget set for " + category + " is invalid. "
                                    + "Please type in a positive number greater than 0");
                        }
                        double oldBudget = budget.getBudgetFromCategory(category);
                        if (oldBudget == addedBudget) {
                            outputValue += "The budget for " + category + " is the same.\n";
                            continue;
                        }
                        budget.addNewBudget(category, addedBudget);
                        outputValue += "You have changed the budget for " + category + " from $"
                                + df.format(oldBudget) + " to $" + df.format(addedBudget) + "\n";
                    } else {
                        throw new MooMooException("Please place the category before the budget "
                                + "and use b/BUDGET to set the budget.\n");
                    }
                }
            }
            ui.setOutput(outputValue.substring(0, outputValue.length() - 1));
            storage.saveBudgetToFile(budget);
        } else if (input.startsWith("list")) {
            if (input.length() == 4) {
                ui.setOutput(budget.toString().substring(0, budget.toString().length() - 1));
            } else {
                input = input.substring(5);
                String[] splitInput = input.split(" ");
                String category = "";

                for (int i = 0; i < splitInput.length; ++i) {
                    if (splitInput[i].startsWith("c/")) {
                        category = splitInput[i].substring(2);
                        if (!inCategoryList(category, catList) || budget.getBudgetFromCategory(category) == 0) {
                            throw new MooMooException("The " + category + " does not exist or"
                                    + " the budget has not been set yet.");
                        }
                        outputValue += budget.toStringCategory(category);
                    }
                }
                if (outputValue == "") {
                    throw new MooMooException("You have not typed in a valid format. "
                            + "Please list down the categories using c/CATEGORY.");
                }
                ui.setOutput(outputValue.substring(0, outputValue.length() - 1));
            }
        } else if (input.startsWith("savings ")) {
            input = input.substring(8);
            boolean hasCategories = false;
            List<String> categories = new ArrayList<String>();
            if (input.length() < 8) {
                throw new MooMooException("OOPS!!! Please indicate the start period (01/2019) after \"s/\" ");
            }
            String startMonth = "";
            String endMonth = "";
            String[] splitInput = input.split(" ");
            outputValue = "";
            for (int i = 0; i < splitInput.length; ++i) {
                if (splitInput[i].startsWith("c/")) {
                    hasCategories = true;
                    categories.add(splitInput[i].substring(2));
                } else if (splitInput[i].startsWith("s/")) {
                    if (startMonth.equals("")) {
                        startMonth = splitInput[i].substring(2);
                    } else {
                        throw new MooMooException("Please only set 1 starting period.");
                    }
                } else if (splitInput[i].startsWith("e/")) {
                    if (endMonth.equals("")) {
                        endMonth = splitInput[i].substring(2);
                    } else {
                        throw new MooMooException("Please only set 1 ending period.");
                    }
                } else {
                    throw new MooMooException("Please use [c/CATEGORY], s/02/2019, e/12/2019");
                }
            }
            LocalDate start = parseMonth(startMonth.strip());
            LocalDate end = parseMonth(endMonth.strip());

            if (!endMonth.equals("") && end == null) {
                throw new MooMooException("Please set your start and end month in this format \"05/2019\"");
            }

            if (!startMonth.equals("") && start == null) {
                throw new MooMooException("Please set your start and end month in this format \"05/2019\"");
            }

            if (end != null && end.isBefore(start)) {
                throw new MooMooException("Your end month is earlier than the start month.");
            }
            if (hasCategories) {
                for (String category : categories) {
                    if (!inCategoryList(category, catList)) {
                        throw new MooMooException("The " + category + " does not exist."
                                + " Please create it first.");
                    }
                    if (budget.getBudgetFromCategory(category) == 0) {
                        throw new MooMooException("The budget for " + category + " does not exist."
                                + "Please set it using budget set.");
                    }
                    if (endMonth.equals("")) {
                        outputValue += "Your savings for " + category + " for " + start.getMonth()
                                + " " + start.getYear() + " is: $"
                                + df.format((budget.getBudgetFromCategory(category) - 0)) + "\n";
                    } else {
                        outputValue += "Your savings for " + category + " from " + start.getMonth() + " "
                                + start.getYear() + " to "
                                + end.getMonth() + " " + end.getYear() + " is: $"
                                + df.format((budget.getBudgetFromCategory(category) - 0)) + "\n";
                    }
                }
            } else {
                if (endMonth.equals("")) {
                    outputValue += "Your total savings for " + start.getMonth() + " " + start.getYear() + " is: ";
                } else {
                    outputValue += "Your total savings from " + start.getMonth() + " " + start.getYear() + " to "
                            + end.getMonth() + " " + end.getYear() + " is: ";
                }
            }
            if (outputValue.endsWith("\n")) {
                ui.setOutput(outputValue.substring(0, outputValue.length() - 1));
            } else {
                ui.setOutput(outputValue);
            }
        } else {
            throw new MooMooException("There is only edit/set/list/savings sub command under budget.");
        }
    }

    /**
     * Runs necessary budgets command and prompts user for input.
     *
     * @param budget    Budget object containing the budget.
     * @param catList   CategoryList object containing the categories
     * @param transList TransactionList object containing transactions within each category.
     * @param ui        Ui object for interaction with user interface.
     * @param storage   Storage object for interaction with filesystem.
     * @throws MooMooException Thrown when error such as invalid input occurs
     */
    private void manageBudgetPrompt(Budget budget, CategoryList catList, TransactionList transList, Ui ui,
                                    Storage storage) throws MooMooException {
        String initialInput = "Please select a job to do (1 - 4):\n1. set (sets budget)\n2. edit (changes budget)\n"
                + "3. list (view budget)\n4. savings (view savings per month)\n5. exit";
        String inputVal = initialInput;
        while (true) {
            String command = ui.confirmPrompt(inputVal);
            try {
                switch (Integer.parseInt(command)) {
                case 1:
                    inputVal = "Please type in the category that you would like to set the budget for, type 0 to exit.";
                    String category = ui.confirmPrompt(inputVal);
                    while (!category.equals("0")) {
                        if (!inCategoryList(category, catList)) {
                            throw new MooMooException(category + " does not exist. Please create it first.");
                        }
                        inputVal = "Please type in the budget that you would like to set for " + category;
                        double addedBudget = 0;
                        try {
                            addedBudget = Double.parseDouble(ui.confirmPrompt(inputVal));
                            if (addedBudget <= 0) {
                                throw new MooMooException("The budget set for " + category + " is invalid. "
                                        + "Please type in a positive number greater than 0");
                            }
                        } catch (NumberFormatException e) {
                            throw new MooMooException("The budget set for " + category + " is invalid. "
                                    + "Please type in a positive number greater than 0");
                        }
                        if (budget.getBudgetFromCategory(category) != 0) {
                            throw new MooMooException("The budget for " + category + " has already been set. "
                                    + "Please edit it using budget edit.");
                        }
                        budget.addNewBudget(category, addedBudget);
                        ui.setOutput("You have set $" + df.format(addedBudget)
                                + " as the budget for " + category + "\n");
                        ui.showResponse();

                        inputVal = "Please type in the category that you would like to set the budget for, "
                                + "type 0 to exit.";
                        category = ui.confirmPrompt(inputVal);
                    }
                    inputVal = initialInput;
                    storage.saveBudgetToFile(budget);
                    continue;
                case 2:
                    inputVal = "Please type in the category that you would like to change the budget for, "
                            + "type 0 to exit.";
                    category = ui.confirmPrompt(inputVal);
                    while (!category.equals("0")) {
                        if (!inCategoryList(category, catList)) {
                            throw new MooMooException(category + " does not exist. Please create it first.");
                        }
                        inputVal = "Please type in the budget that you would like to change to for " + category;
                        double addedBudget = 0;
                        try {
                            addedBudget = Double.parseDouble(ui.confirmPrompt(inputVal));
                            if (addedBudget <= 0) {
                                throw new MooMooException("The budget set for " + category + " is invalid. "
                                        + "Please type in a positive number greater than 0");
                            }
                        } catch (NumberFormatException e) {
                            throw new MooMooException("The budget to set for " + category + " is invalid. "
                                    + "Please type in a positive number greater than 0");
                        }
                        if (budget.getBudgetFromCategory(category) == 0) {
                            throw new MooMooException("The budget for " + category + " has already been set. "
                                    + "Please edit it using budget edit.");
                        }
                        double oldBudget = budget.getBudgetFromCategory(category);
                        if (oldBudget == addedBudget) {
                            ui.setOutput("The budget for " + category + " is the same.\n");
                            inputVal = "Please type in the category that you would like to set the budget for, "
                                    + "type 0 to exit.";
                            category = ui.confirmPrompt(inputVal);
                            continue;
                        }

                        budget.addNewBudget(category, addedBudget);
                        ui.setOutput("You have changed the budget for " + category + " from $"
                                + df.format(oldBudget) + " to $" + df.format(addedBudget) + "\n");
                        ui.showResponse();

                        inputVal = "Please type in the category that you would like to set the budget for, "
                                + "type 0 to exit.";
                        category = ui.confirmPrompt(inputVal);
                    }
                    inputVal = initialInput;
                    storage.saveBudgetToFile(budget);
                    continue;
                case 3:
                    inputVal = "Please type in the category that you would like to view the budget for, "
                            + "type 0 to exit or all to view all categories.";
                    category = ui.confirmPrompt(inputVal);
                    while (!category.equals("0")) {
                        if (category.equals("all")) {
                            ui.setOutput(budget.toString().substring(0, budget.toString().length() - 1));
                            ui.showResponse();
                            continue;
                        }

                        if (!inCategoryList(category, catList)) {
                            throw new MooMooException(category + " does not exist. Please create it first.");
                        }

                        ui.setOutput(budget.toStringCategory(category));
                        ui.showResponse();

                        inputVal = "Please type in the category that you would like to view the budget for, "
                                + "type 0 to exit or all to view all categories.";
                        category = ui.confirmPrompt(inputVal);
                    }
                    inputVal = initialInput;
                    continue;
                case 4:
                    inputVal = "Please type in the category that you would like to view your savings for, "
                            + "type 0 to exit or leave it blank to view all.";
                    category = ui.confirmPrompt(inputVal);
                    while (!category.equals("0")) {
                        if (!category.equals("") && !inCategoryList(category, catList)) {
                            throw new MooMooException(category + " does not exist. Please create it first.");
                        }
                        inputVal = "Please type in the start month and year (01/2019).";
                        String startMonth = ui.confirmPrompt(inputVal);

                        inputVal = "Please type in the end period or leave blank to view for a month (10/2019).";
                        String endMonth = ui.confirmPrompt(inputVal);

                        LocalDate start = parseMonth(startMonth.strip());
                        LocalDate end = parseMonth(endMonth.strip());

                        if (!endMonth.equals("") && end == null) {
                            throw new MooMooException("Please set your start and end month in this format \"05/2019\"");
                        }

                        if (!startMonth.equals("") && start == null) {
                            throw new MooMooException("Please set your start and end month in this format \"05/2019\"");
                        }

                        if (end != null && end.isBefore(start)) {
                            throw new MooMooException("Your end month is earlier than the start month.");
                        }

                        if (category.equals("")) {
                            if (endMonth.equals("")) {
                                ui.setOutput("Your total savings for " + start.getMonth()
                                        + " " + start.getYear() + " is: ");
                            } else {
                                ui.setOutput("Your total savings from " + start.getMonth()
                                        + " " + start.getYear() + " to "
                                        + end.getMonth() + " " + end.getYear() + " is: ");
                            }
                        } else {
                            if (!inCategoryList(category, catList)) {
                                throw new MooMooException("The " + category + " does not exist."
                                        + " Please create it first.");
                            }
                            if (budget.getBudgetFromCategory(category) == 0) {
                                throw new MooMooException("The budget for " + category + " does not exist."
                                        + "Please set it using budget set.");
                            }
                            if (endMonth.equals("")) {
                                ui.setOutput("Your savings for " + category + " for " + start.getMonth()
                                        + " " + start.getYear() + " is: $"
                                        + df.format((budget.getBudgetFromCategory(category) - 0)) + "\n");
                            } else {
                                ui.setOutput("Your savings for " + category + " from " + start.getMonth() + " "
                                        + start.getYear() + " to "
                                        + end.getMonth() + " " + end.getYear() + " is: $"
                                        + df.format((budget.getBudgetFromCategory(category) - 0)) + "\n");
                            }
                        }
                        ui.showResponse();
                        inputVal = "Please type in the category that you would like to view your savings for, "
                                + "type 0 to exit or leave it blank to view all.";
                        category = ui.confirmPrompt(inputVal);
                    }
                    inputVal = initialInput;
                    continue;
                case 5:
                    ui.setOutput("Returning to main menu.");
                    ui.showResponse();
                    return;
                default:
                    inputVal = "Please select a number from 1 to 4";
                    continue;
                }
            } catch (NumberFormatException e) {
                inputVal = "Please select a number from 1 to 4";
                continue;
            }
        }
    }

    private boolean inCategoryList(String value, CategoryList catList) {
        for (Category cat : catList.getCategoryList()) {
            if (cat.getName().equals(value)) {
                return true;
            }
        }
        return false;
    }

    
}