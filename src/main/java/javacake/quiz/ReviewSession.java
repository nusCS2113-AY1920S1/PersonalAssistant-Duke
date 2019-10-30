package javacake.quiz;

import javacake.commands.BackCommand;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.Logic;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import static javacake.quiz.QuestionList.MAX_QUESTIONS;

public class ReviewSession implements QuizManager {

    private QuestionList answeredQuestions;
    private boolean isExitReview = false;

    /**
     * ReviewSession constructor to load the list of questions to review.
     *
     * @param questionList list of questions from a quiz session. userAnswer in all questions must not be null.
     */
    public ReviewSession(QuestionList questionList) {
        answeredQuestions = questionList;
    }

    /**
     * Method to get question string, user answer and correct answer strings.
     *
     * @param index the index of the question, between 0 and MAX_QUESTIONS-1.
     * @return the string to be outputted.
     */
    @Override
    public String getQuestion(int index) {
        String message = "Type the question number to navigate to that question.\n"
                + "Type \"back\" to return to table of contents.\n";
        return message + answeredQuestions.getQuestion(index) + "\n\n" + answeredQuestions.getAnswers(index);
    }

    /**
     * Parses valid user input for review session.
     * @param index index of current question. Unused.
     * @param input user input which can be index which is valid between 1 and MAX_QUESTIONS, or "back".
     * @return Valid question index between 0 and MAX_QUESTIONS-1, or BackCommand identifier.
     * @throws DukeException if input is neither a valid question index or "back".
     */
    @Override
    public String parseInput(int index, String input) throws DukeException {
        if (input.equals("back")) {
            // TODO tie BackCommand identifier to MainWindow
            return "!@#_BACK";
        } else if (isValidInput(input)) {
            int tmp = Integer.parseInt(input) - 1;
            return String.valueOf(tmp); // echo back input with proper indexing for the next getQuestion
        } else {
            throw new DukeException("Invalid command at this point in the program.");
        }
    }

    /**
     * Executes the review of a quiz after a quiz is completed. For CLI mode.
     *
     * @param logic how far the program is currently in in the table of contents.
     * @param ui the UI responsible for inputs and outputs of the program.
     * @param storageManager storage container.
     * @return execution of back command when input is equal to "back".
     * @throws DukeException This method does not throw this exception.
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        int index = 0;
        while (!isExitReview) {
            ui.showLine();
            try {
                Question question = answeredQuestions.getQuestionList().get(index);
                ui.displayReview(question, index + 1, answeredQuestions.getQuestionList().size());
                String next = ui.readCommand();
                if (next.trim().equals("back")) {
                    isExitReview = true;
                } else {
                    index = Integer.parseInt(next) - 1;
                }
            } catch (IndexOutOfBoundsException e) {
                ui.showError("Invalid index! Range of question: 1 - " + answeredQuestions.getQuestionList().size());
                index = 0;
            } catch (NumberFormatException e) {
                ui.showError("That isn't a number! Try again.");
            }
        }
        ui.showLine();
        return new BackCommand().execute(logic, ui, storageManager);
    }

    private static boolean isValidInput(String input) {
        int tmp;
        try {
            tmp = Integer.parseInt(input);
            if (tmp > MAX_QUESTIONS || tmp <= 0) {
                return false;
            }
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
