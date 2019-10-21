package javacake.commands;

import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.ProgressStack;
import javacake.storage.Storage;
import javacake.ui.Ui;
import javacake.quiz.Question;

import java.util.ArrayList;

public class ReviewCommand extends Command {

    private ArrayList<Question> answeredQuestions;
    private boolean isExitReview = false;

    /**
     * ReviewCommand constructor to load the list of questions to review.
     *
     * @param chosenQuestions list of questions from a quiz session. userAnswer in all questions must not be null.
     */
    public ReviewCommand(ArrayList<Question> chosenQuestions) {
        answeredQuestions = chosenQuestions;
    }

    /**
     * Executes the review of a quiz after a quiz is completed.
     *
     * @param progressStack how far the program is currently in in the table of contents.
     * @param ui the UI responsible for inputs and outputs of the program.
     * @param storage Storage to write updated data.
     * @param profile Profile of the user.
     * @throws DukeException This method does not throw this exception.
     * @return
     */
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        int index = 0;
        while (!isExitReview) {
            ui.showLine();
            try {
                Question question = answeredQuestions.get(index);
                ui.displayReview(question, index + 1, answeredQuestions.size());
                String next = ui.readCommand();
                if (next.trim().equals("back")) {
                    isExitReview = true;
                } else {
                    index = Integer.parseInt(next) - 1;
                }
            } catch (IndexOutOfBoundsException e) {
                ui.showError("Invalid index! Range of question: 1 - " + answeredQuestions.size());
                index = 0;
            } catch (NumberFormatException e) {
                ui.showError("That isn't a number! Try again.");
            }
        }
        ui.showLine();
        return new BackCommand().execute(progressStack, ui, storage, profile);
    }
}
