package moomoo.command;

import moomoo.task.*;

/**
 * Represents the command to exit the program.
 */
public class ExitCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise.
     *               Value should be true in this class.
     * @param input Input given by the user
     */
    public ExitCommand(boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(ScheduleList Calendar, Budget budget, CategoryList catList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        ui.showGoodbye();
    }
}