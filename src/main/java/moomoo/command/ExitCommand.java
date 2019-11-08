package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;
import moomoo.task.category.CategoryList;
import moomoo.task.category.Category;

import java.io.IOException;

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
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c",
                    "chcp", "437", ">", "nul", "2>&1").inheritIO();
            pb.start();
        } catch (IOException e) {
            throw new MooMooException("An error has occurred. Please close the terminal.");
        }
        ui.showGoodbye();
    }
}