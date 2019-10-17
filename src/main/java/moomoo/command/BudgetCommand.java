package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.TransactionList;
import moomoo.task.ScheduleList;
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
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, TransactionList transList, Ui ui, Storage storage)
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
            boolean reset = false;
            for (int i = 0; i < splitInput.length; ++i) {
                if (i % 2 == 0) {
                    if (splitInput[i].startsWith("c/")) {
                        category = splitInput[i].substring(2);
                        if (splitInput[i + 1].startsWith("b/")) {
                            if (!inCategoryList(category, catList)) { //check if category exists.
                                throw new MooMooException(category + " does not exist. Please create it first.");
                            }
                        }
                    } else {
                        if (!reset) {
                            category += " " + splitInput[i];
                            continue;
                        }
                        throw new MooMooException("Please place the category before the budget "
                                + "and use c/CATEGORY to set the category.\n");
                    }
                } else {
                    if (splitInput[i].startsWith("b/")) {
                        reset = true;
                        if (!inCategoryList(category, catList)) {
                            throw new MooMooException(category + " does not exist. Please create it first.");
                        }
                        outputValue += addBudget(budget, splitInput[i].substring(2), category);
                    } else {
                        if (!reset) {
                            category += " " + splitInput[i];
                            continue;
                        }
                        throw new MooMooException("Please place the category before the budget "
                                + "and use b/BUDGET to set the budget.\n");
                    }
                }
            }

            if (outputValue == "") {
                throw new MooMooException("You have not typed in a valid format. "
                        + " Please use budget set c/CATEGORY b/BUDGET.");
            }
            ui.setOutput(outputValue.substring(0, outputValue.length() - 1));
            storage.saveBudgetToFile(budget);
        } else if (input.startsWith("edit ")) {
            input = input.substring(5);
            String[] splitInput = input.split(" ");
            String category = "";
            boolean reset = false;
            for (int i = 0; i < splitInput.length; ++i) {
                if (i % 2 == 0) {
                    if (splitInput[i].startsWith("c/")) {
                        category = splitInput[i].substring(2);
                        if (splitInput[i + 1].startsWith("b/")) {
                            if (!inCategoryList(category, catList)) { //check if category exists.
                                throw new MooMooException("The " + category
                                        + " does not exist. Please create it first.");
                            }
                        }
                    } else {
                        if (!reset) {
                            category += " " + splitInput[i];
                            continue;
                        }
                        throw new MooMooException("Please place the category before the budget and "
                                + "use c/CATEGORY to set the category.");
                    }
                } else {
                    if (splitInput[i].startsWith("b/")) {
                        reset = true;
                        if (!inCategoryList(category, catList)) {
                            throw new MooMooException(category + " does not exist. Please create it first.");
                        }
                        if (budget.getBudgetFromCategory(category) == 0) {
                            throw new MooMooException("The budget for " + category + " does not exist."
                                    + " Please set it using budget set.");
                        }

                        outputValue += editBudget(budget, splitInput[i].substring(2), category);

                    } else {
                        if (!reset) {
                            category += " " + splitInput[i];
                            continue;
                        }
                        throw new MooMooException("Please place the category before the budget "
                                + "and use b/BUDGET to set the budget.\n");
                    }
                }
            }
            if (outputValue == "") {
                throw new MooMooException("You have not typed in a valid format. "
                        + " Please use budget set c/CATEGORY b/BUDGET.");
            }
            ui.setOutput(outputValue.substring(0, outputValue.length() - 1));
            storage.saveBudgetToFile(budget);
        } else if (input.startsWith("list")) {
            if (input.length() == 4) {
                if (budget.getBudgetSize() > 0) {
                    ui.setOutput(budget.toString().substring(0, budget.toString().length() - 1));
                } else {
                    throw new MooMooException("You have not set your budget for any categories.");
                }
            } else {
                input = input.substring(5);
                String[] splitInput = input.split(" ");
                String category = "";
                boolean reset = false;
                for (int i = 0; i < splitInput.length; ++i) {
                    if (splitInput[i].startsWith("c/")) {
                        if (i > 0) {
                            reset = true;
                        }
                        category = splitInput[i].substring(2);
                        if (i < (splitInput.length - 1) && splitInput[i + 1].startsWith("c/")) {
                            outputValue += addOutputValue(category, catList, budget);
                        } else if (i == (splitInput.length - 1)) {
                            outputValue += addOutputValue(category, catList, budget);
                        }
                    } else {
                        if (!reset) {
                            category += " " + splitInput[i];
                        }
                        if (i < (splitInput.length - 1) && splitInput[i + 1].startsWith("c/")) {
                            outputValue += addOutputValue(category, catList, budget);
                        } else if (i == (splitInput.length - 1)) {
                            outputValue += addOutputValue(category, catList, budget);

                        }
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
            if (hasCategories) {
                outputValue += viewSavings(budget, ui, catList, startMonth, endMonth, "", categories);
            } else {
                outputValue += viewSavings(budget, ui, catList, startMonth, endMonth, "", null);
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
                    String category = initialPrompt(catList, ui, budget,
                            "Please type in the category that you would like to set the budget for, "
                                    + "type 0 to exit.");
                    while (!category.equals("0")) {
                        if (!inCategoryList(category, catList)) {
                            ui.setOutput(category + " does not exist. Please create it first.");
                            ui.showResponse();
                            category = initialPrompt(catList, ui, budget,
                                    "Please type in the category that you would like to set the budget for, "
                                            + "type 0 to exit.");
                            continue;
                        }

                        inputVal = "\nPlease type in the budget that you would like to set for " + category;
                        String inputBudget = ui.confirmPrompt(inputVal);

                        ui.setOutput(addBudget(budget, inputBudget, category));
                        ui.showResponse();

                        category = initialPrompt(catList, ui, budget,
                                "Please type in the category that you would like to set the budget for, "
                                        + "type 0 to exit.");
                    }
                    inputVal = initialInput;
                    storage.saveBudgetToFile(budget);
                    continue;
                case 2:
                    if (budget.getBudgetSize() == 0) {
                        ui.setOutput("You have yet to set a budget for any category. Please set at least one.");
                        ui.showResponse();
                        inputVal = initialInput;
                        continue;
                    }

                    category = initialPrompt(catList, ui, budget,
                            "Please type in the category that you would like to change the budget for, "
                                    + "type 0 to exit");

                    while (!category.equals("0")) {
                        if (!inCategoryList(category, catList)) {
                            ui.setOutput(category + " does not exist. Please create it first.");
                            ui.showResponse();
                            category = initialPrompt(catList, ui, budget,
                                    "Please type in the category that you would like to change the budget for, "
                                            + "type 0 to exit.");
                            continue;
                        }
                        inputVal = "Please type in the budget that you would like to change to for " + category;
                        String inputBudget = ui.confirmPrompt(inputVal);

                        ui.setOutput(editBudget(budget, inputBudget, category));
                        ui.showResponse();

                        category = initialPrompt(catList, ui, budget,
                                "Please type in the category that you would like to change the budget for, "
                                        + "type 0 to exit.");
                    }
                    inputVal = initialInput;
                    storage.saveBudgetToFile(budget);
                    continue;
                case 3:
                    if (budget.getBudgetSize() == 0) {
                        ui.setOutput("You have yet to set a budget for any category. Please set at least one.");
                        ui.showResponse();
                        inputVal = initialInput;
                        continue;
                    }

                    category = initialPrompt(catList, ui, budget,
                            "Please type in the category that you would like to view the budget for, "
                                    + "type 0 to exit or leave blank to view all categories.");

                    while (!category.equals("0")) {
                        if (category.equals("")) {
                            ui.setOutput(budget.toString().substring(0, budget.toString().length() - 1));
                            ui.showResponse();
                            category = initialPrompt(catList, ui, budget,
                                    "Please type in the category that you would like to view the budget for, "
                                            + "type 0 to exit or leave blank to view all categories.");
                            continue;
                        }

                        if (!inCategoryList(category, catList)) {
                            ui.setOutput(category + " does not exist. Please create it first.");
                            ui.showResponse();
                            category = initialPrompt(catList, ui, budget,
                                    "Please type in the category that you would like to view the budget for, "
                                            + "type 0 to exit or leave blank to view all categories.");
                            continue;
                        }

                        ui.setOutput(budget.toStringCategory(category));
                        ui.showResponse();

                        category = initialPrompt(catList, ui, budget,
                                "Please type in the category that you would like to view the budget for, "
                                        + "type 0 to exit or leave blank to view all categories.");
                    }
                    inputVal = initialInput;
                    continue;
                case 4:
                    if (budget.getBudgetSize() == 0) {
                        ui.setOutput("You have yet to set a budget for any category. Please set at least one.");
                        ui.showResponse();
                        inputVal = initialInput;
                        continue;
                    }
                    category = initialPrompt(catList, ui, budget,
                            "Please type in the category that you would like to view your savings for, "
                                    + "type 0 to exit or leave it blank to view all.");

                    while (!category.equals("0")) {
                        if (!category.equals("") && !inCategoryList(category, catList)) {
                            ui.setOutput(category + " does not exist. Please create it first.");
                            ui.showResponse();
                            category = initialPrompt(catList, ui, budget,
                                    "Please type in the category that you would like to view your savings for, "
                                            + "type 0 to exit or leave it blank to view all.");
                            continue;
                        }

                        inputVal = "Please type in the start month and year (01/2019).";
                        String startMonth = ui.confirmPrompt(inputVal);

                        inputVal = "Please type in the end period or leave blank to view for a month (10/2019).";
                        String endMonth = ui.confirmPrompt(inputVal);

                        ui.setOutput(viewSavings(budget, ui, catList, startMonth, endMonth, category, null));
                        ui.showResponse();

                        category = initialPrompt(catList, ui, budget,
                                "Please type in the category that you would like to view your savings for, "
                                        + "type 0 to exit or leave it blank to view all.");
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
            if (cat.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }

    private String addBudget(Budget budget, String inputBudget, String category) throws MooMooException {
        double addedBudget = 0;
        String outputValue;
        try {
            addedBudget = Double.parseDouble(inputBudget);
            if (addedBudget <= 0) {
                outputValue = "The budget set for " + category + " is invalid. "
                        + "Please type in a positive number greater than 0";
                return outputValue;
            }
        } catch (NumberFormatException e) {
            outputValue = "The budget set for " + category + " is invalid. "
                    + "Please type in a positive number greater than 0";
            return outputValue;
        }
        if (budget.getBudgetFromCategory(category) != 0) {
            outputValue = "The budget for " + category + " has already been set. "
                    + "Please edit it using budget edit.";
            return outputValue;
        }
        budget.addNewBudget(category, addedBudget);
        outputValue = "You have set $" + df.format(addedBudget)
                + " as the budget for " + category + "\n";

        return outputValue;
    }

    private String editBudget(Budget budget, String inputBudget, String category) throws MooMooException {
        double addedBudget = 0;
        String outputValue;
        try {
            addedBudget = Double.parseDouble(inputBudget);
            if (addedBudget <= 0) {
                outputValue = "The budget set for " + category + " is invalid. "
                        + "Please type in a positive number greater than 0";
                return outputValue;
            }
        } catch (NumberFormatException e) {
            outputValue = "The budget set for " + category + " is invalid. "
                    + "Please type in a positive number greater than 0";
            return outputValue;
        }

        if (budget.getBudgetFromCategory(category) == 0) {
            outputValue = "The budget for " + category + " has already been set. "
                    + "Please edit it using budget edit.";
            return outputValue;
        }

        double oldBudget = budget.getBudgetFromCategory(category);

        if (oldBudget == addedBudget) {
            outputValue = "The budget for " + category + " is the same.\n";
            return outputValue;
        }

        budget.addNewBudget(category, addedBudget);
        outputValue = "You have changed the budget for " + category + " from $"
                + df.format(oldBudget) + " to $" + df.format(addedBudget) + "\n";

        return outputValue;
    }

    private String viewSavings(Budget budget, Ui ui, CategoryList catList, String startMonth,
                               String endMonth, String category, List<String> categories) throws MooMooException {
        String outputValue = "";
        LocalDate start = parseMonth(startMonth.strip());
        LocalDate end = parseMonth(endMonth.strip());

        if (!endMonth.equals("") && end == null) {
            outputValue = "Please set your start and end month in this format \"05/2019\"";
            return outputValue;
        }

        if (!startMonth.equals("") && start == null) {
            outputValue = "Please set your start and end month in this format \"05/2019\"";
            return outputValue;
        }

        if (end != null && end.isBefore(start)) {
            outputValue = "Your end month is earlier than your start month.";
            return outputValue;
        }

        if (categories == null && category.equals("")) {
            if (endMonth.equals("")) {
                outputValue = "Your total savings for " + start.getMonth()
                        + " " + start.getYear() + " is: ";
            } else {
                outputValue = "Your total savings from " + start.getMonth()
                        + " " + start.getYear() + " to "
                        + end.getMonth() + " " + end.getYear() + " is: ";
            }
        } else {
            if (!category.equals("") && categories == null) {
                categories = new ArrayList<>();
                categories.add(category);
            }
            for (String iteratorCategory : categories) {
                if (!inCategoryList(iteratorCategory, catList)) {
                    throw new MooMooException("The " + iteratorCategory + " does not exist."
                            + " Please create it first.");
                }
                if (budget.getBudgetFromCategory(iteratorCategory) == 0) {
                    throw new MooMooException("The budget for " + iteratorCategory + " does not exist."
                            + "Please set it using budget set.");
                }
                if (endMonth.equals("")) {
                    outputValue += "Your savings for " + iteratorCategory + " for " + start.getMonth()
                            + " " + start.getYear() + " is: $"
                            + df.format((budget.getBudgetFromCategory(iteratorCategory) - 0)) + "\n";
                } else {
                    outputValue += "Your savings for " + iteratorCategory + " from " + start.getMonth() + " "
                            + start.getYear() + " to "
                            + end.getMonth() + " " + end.getYear() + " is: $"
                            + df.format((budget.getBudgetFromCategory(iteratorCategory) - 0)) + "\n";
                }
            }
        }
        return outputValue;
    }

    private String initialPrompt(CategoryList catList, Ui ui, Budget budget, String promptValue) {
        String outputValue = "CATEGORIES:\n";
        for (int i = 0; i < catList.getCategoryList().size(); ++i) {
            String categoryName = catList.getCategoryList().get(i).toString();
            outputValue += categoryName + "\n";
        }
        if (outputValue.equals("CATEGORIES:\n")) {
            outputValue += "There are no categories set.\n";
        }
        String inputVal = outputValue + promptValue;
        String category = ui.confirmPrompt(inputVal);
        return category;
    }

    private String addOutputValue(String category, CategoryList catList, Budget budget) throws MooMooException {
        String outputValue = "";
        if (!inCategoryList(category, catList) || budget.getBudgetFromCategory(category) == 0) {
            throw new MooMooException("The " + category + " does not exist or"
                    + " the budget has not been set yet.");
        }
        outputValue = budget.toStringCategory(category);
        return outputValue;
    }
}