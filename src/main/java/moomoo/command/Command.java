package moomoo.command;

import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.CategoryList;
import moomoo.task.TransactionList;
import moomoo.task.Ui;
import moomoo.task.Storage;


/**
 * Represents the various commands to be executed.
 */
public class Command {
    public boolean isExit;
    protected String input;

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    Command(boolean isExit, String input) {
        this.isExit = isExit;
        this.input = input;
    }


    /**
     * Executes necessary functions for each different command.
     * @param budget Budget object containing the budget.
     * @param categoryList CategoryList object containing the categories
     * @param transList TransactionList object containing transactions within each category.
     * @param ui Ui object for interaction with user interface.
     * @param storage Storage object for interaction with filesystem.
     * @throws MooMooException Thrown when error such as invalid input occurs
     */
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList, TransactionList transList,
                        Ui ui, Storage storage)
            throws MooMooException {

    }
}
