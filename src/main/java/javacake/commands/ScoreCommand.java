package javacake.commands;

import javacake.DukeException;
import javacake.Profile;
import javacake.ProgressStack;
import javacake.Storage;
import javacake.Ui;

public class ScoreCommand extends Command {

    public ScoreCommand() {

    }

    /**
     * Executes showing quiz score.
     * @param progressStack TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     */
    @Override
    public void execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) {
        ui.showMessage(ui.getQuizResults(profile.getTotalProgress()).trim());
    }
}
