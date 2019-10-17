package moomoo.command;

import moomoo.task.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the command to manage budget.
 */
public class BudgetCommand extends Command {
    private DecimalFormat df;

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public BudgetCommand(boolean isExit, String input) {
        super(isExit, input);
        df = new DecimalFormat("#.00");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        if (input.length() < 7) {
            throw new MooMooException("OOPS!!! Please use set/edit/savings/list sub command");
        }
        input = input.substring(7);
        if (input.startsWith("set ")) {
            if (budget.getBudget() != 0.0) {
                throw new MooMooException("Your budget has already been set, please use the edit sub command "
                        + "to change your budget.");
            } else {
                addBudget(budget, ui, storage);
            }
        } else if (input.equals("list")) {
            if (budget.getBudget() != 0.0) {
                ui.setOutput(budget.toString());
            } else {
                throw new MooMooException("You have yet to set your budget.");
            }
        } else if (input.startsWith("savings ")) {
            int startIndex = input.indexOf("s/");
            if (startIndex == -1) {
                throw new MooMooException("OOPS!!! Please indicate the start period after \"s/\"");
            }
            int endIndex = input.indexOf("e/");
            String startMonth;
            String endMonth;
            if (endIndex != -1) {
                startMonth = input.substring(startIndex + 2, endIndex);
                endMonth = input.substring(endIndex + 2);
                LocalDate start = parseMonth(startMonth.strip());
                LocalDate end = parseMonth(endMonth.strip());

                if (start == null || end == null) {
                    throw new MooMooException("Please set your start and end month in this format \"05/2019\"");
                }
                if (end.isBefore(start)) {
                    throw new MooMooException("Your end month is earlier than the start month.");
                }
                ui.setOutput("Your savings from " + start.getMonth() + " " + start.getYear() + " to "
                        + end.getMonth() + " " + end.getYear() + " is: ");
            } else {
                startMonth = input.substring(startIndex + 2);
                LocalDate start = parseMonth(startMonth.strip());

                if (start == null) {
                    throw new MooMooException("Please set your start month in this format \"05/2019\"");
                }
                ui.setOutput("Your savings for " + start.getMonth() + " " + start.getYear() + " is: ");
            }
        } else if (input.startsWith("edit ")) {
            if (budget.getBudget() != 0.0) {
                addBudget(budget, ui, storage);
            } else {
                ui.setOutput("Please set your budget using budget set");
            }

        } else {
            throw new MooMooException("There is only edit/set/list/savings sub command under budget.");
        }
    }

    /**
     * Adds or changes the budget as given by user.
     * @param budget Current Budget
     * @param ui Current Ui
     * @param storage Current Storage
     * @throws MooMooException thrown when user input is invalid.
     */
    private void addBudget(Budget budget, Ui ui, Storage storage) throws MooMooException {
        int amountIndex = input.indexOf("d/");
        if (amountIndex == -1) {
            throw new MooMooException("OOPS!!! Please indicate the amount after \"d/\"");
        }
        double amount;
        try {
            amount = Double.parseDouble(input.substring(amountIndex + 2));
        } catch (Exception e) {
            throw new MooMooException("OOPS!!! Please indicate the amount after \"d/\"");
        }
        double currentBudget = budget.getBudget();
        if (currentBudget == amount) {
            throw new MooMooException("Your new budget is the same as the old budget.");
        } else if (currentBudget != 0) {
            String confirm = ui.confirmPrompt();
            if (confirm.toLowerCase().equals("y")) {
                budget.setBudget(amount);
                ui.setOutput("You have set your new budget to be $" + df.format(amount) + " every month.");
                storage.saveBudgetToFile(budget);
                return;
            }
        }
        budget.setBudget(amount);
        ui.setOutput("You have set your budget to be $" + df.format(amount) + " every month.");
        storage.saveBudgetToFile(budget);
    }

    private LocalDate parseMonth(String inputDate) {
        try {
            inputDate = "01/" + inputDate;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate parsedDate = LocalDate.parse(inputDate, formatter);
            return parsedDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}