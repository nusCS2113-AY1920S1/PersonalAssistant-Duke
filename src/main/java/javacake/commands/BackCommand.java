package javacake.commands;

import javacake.DukeException;
import javacake.ProgressStack;
import javacake.Profile;
import javacake.Ui;
import javacake.Storage;

import java.io.IOException;

public class BackCommand extends Command {

    public BackCommand() {
        type = CmdType.BACK;
    }

    /**
     * Execute going back to previous index.
     * @param progressStack TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @throws DukeException Error thrown when unable to close file reader
     */
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException, IOException {

        progressStack.backToPreviousPath();
        progressStack.insertQueries();
        if (progressStack.containsDirectory()) {
            return (progressStack.displayDirectories());
        } else {
            progressStack.updateFilePath(progressStack.gotoFilePath(0));
            return (progressStack.readQuery());
        }
    }
}
