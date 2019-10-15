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
        try {
            if (progressStack.containsDirectory()) {
                return (progressStack.displayDirectories());
            } else {
                progressStack.updateFilePath(progressStack.gotoFilePath(0));
                return (progressStack.readQuery());
            }
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
        /*if (progressStack.checkProgress() == 2) {
            progressStack.listIndexToMainList();
            ListCommand listCommand = new ListCommand();
            listCommand.execute(progressStack, ui, storage, profile);

        } else if (progressStack.checkProgress() == 1) {
            ListCommand listCommand = new ListCommand();
            listCommand.execute(progressStack, ui, storage, profile);

        } else if (progressStack.checkProgress() == 3) {
            int previousState = progressStack.checkPreviousState();

            if (previousState == 3) {
                progressStack.clearCurrentState();
                GoToCommand goToCommand = new GoToCommand("goto 3");
                goToCommand.execute(progressStack, ui, storage, profile);

            } else if (previousState == 2) {
                progressStack.clearCurrentState();
                GoToCommand goToCommand = new GoToCommand("goto 2");
                goToCommand.execute(progressStack, ui, storage, profile);

            } else if (previousState == 1) {
                progressStack.clearCurrentState();
                GoToCommand goToCommand = new GoToCommand("goto 1");
                goToCommand.execute(progressStack, ui, storage, profile);
            }
        }*/
    }
}
