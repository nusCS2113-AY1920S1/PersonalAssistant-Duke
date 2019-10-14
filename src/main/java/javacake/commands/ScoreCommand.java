package javacake.commands;

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
     * @return
     */
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) {
        return Ui.getQuizResults(profile.getTotalProgress());

    }

    /**
     * Method to get quiz score.
     * @param progress the user's overall quiz score
     * @return String with quiz score message
     */
    private String getQuizResults(int progress) {
        StringBuilder str = new StringBuilder();
        str.append("Here's your quiz progress so far :D\n");
        for (int i = 0; i < 12; ++i) {
            if (i < progress) {
                str.append("#");
            } else {
                str.append("-");
            }
        }
        progress = progress * 100 / 12;
        if (progress == 99) {
            progress = 100;
        }
        str.append(" ").append(progress).append("%").append("\n");
        return str.toString();
    }
}
