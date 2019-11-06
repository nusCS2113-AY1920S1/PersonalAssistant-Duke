package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.quiz.QuestionList;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ScoreCommand extends Command {
    //3 different levels: EZ, MED, HARD
    private int questionDifficulties = 3;
    //Number of questions per level
    private int questionSize = QuestionList.MAX_QUESTIONS;
    //4 different quizzes
    private int questionTypes = 4;
    private int totalQuestionQuantum = questionDifficulties * questionTypes * questionSize;

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
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        return getQuizResults(storageManager.profile);

    }

    /**
     * Method to get quiz score.
     * @param profile the user's profile
     * @return String with quiz score message
     */

    private String getQuizResults(Profile profile) throws CakeException {
        try {
            StringBuilder str = new StringBuilder();
            str.append("Here's your quiz progress so far :D\n");
            str.append("(");
            for (int i = 0; i < questionTypes; ++i) {
                str.append(" ");
                for (int j = 0; j < questionDifficulties; ++j) {
                    for (int k = 0; k < questionSize; ++k) {
                        if (k < profile.getIndividualContentMarks((i * questionDifficulties) + j)) {
                            str.append("#");
                        } else {
                            str.append("-");
                        }
                    }
                    str.append(" ");
                }
                if (i != questionTypes - 1) {
                    str.append("|");
                }
            }
            str.append(") ");
            int progress = (int) ((double) profile.getTotalProgress() / totalQuestionQuantum * 100);
            str.append(progress).append("%");
            return str.toString();
        } catch (Exception e) {
            throw new CakeException("Null pointer exception here");
        }
    }
}
