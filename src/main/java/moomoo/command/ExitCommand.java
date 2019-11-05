package moomoo.command;

import moomoo.feature.ScheduleList;
import moomoo.feature.Budget;
import moomoo.feature.category.CategoryList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;

/**
 * Represents the command to exit the program.
 */
public class ExitCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise.
     *               Value should be true in this class.
     */
    public ExitCommand(boolean isExit) {
        super(isExit, "");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryListList,
                        Ui ui, Storage storage) {
        ui.showGoodbye();
    }
}