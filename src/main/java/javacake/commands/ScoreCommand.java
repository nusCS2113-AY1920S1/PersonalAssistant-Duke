package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ScoreCommand extends Command {
    //3 different grades: BAD, OKAY, GOOD
    private int questionGrades = 3;
    //4 different quizzes
    private int questionTypes = 4;
    private int totalQuestionQuantum = questionGrades * questionTypes;

    /**
     * Constructor for ScoreCommand.
     * Checks that no parameters are included.
     * @param inputCommand Score command from user.
     * @throws CakeException If other parameter is appended to command.
     */
    public ScoreCommand(String inputCommand) throws CakeException {
        checksParam(inputCommand);
    }

    /**
     * Executes showing quiz score.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) {
        return Ui.getQuizResults(storageManager.profile.getTotalProgress());

    }

    /**
     * Method to get quiz score.
     * @param progress the user's overall quiz score
     * @return String with quiz score message
     */
    private String getQuizResults(int progress) {
        StringBuilder str = new StringBuilder();
        str.append("Here's your quiz progress so far :D\n");
        for (int i = 0; i < totalQuestionQuantum; ++i) {
            if (i < progress) {
                str.append("#");
            } else {
                str.append("-");
            }
        }
        progress = progress * 100 / totalQuestionQuantum;
        if (progress == 99) {
            progress = 100;
        }
        str.append(" ").append(progress).append("%").append("\n");
        return str.toString();
    }
}
