package moomoo.command;

import moomoo.feature.ScheduleList;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.Storage;

/**
 * Represents the various commands to be executed.
 */
public abstract class Command {
    public boolean isExit;
    protected String input;

    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public Command(boolean isExit, String input) {
        this.isExit = isExit;
        this.input = input;
    }


    /**
     * Executes necessary functions for each different command.
     * @param budget Budget object containing the budget.
     * @param categoryList CategoryList object containing the categories
     * @param storage Storage object for interaction with filesystem.
     * @throws MooMooException Thrown when error such as invalid input occurs
     */
    public abstract void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                                 Storage storage)
            throws MooMooException;
}
