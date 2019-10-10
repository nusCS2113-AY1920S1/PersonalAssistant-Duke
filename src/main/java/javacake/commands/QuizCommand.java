package javacake.commands;

import javacake.DukeException;
import javacake.Profile;
import javacake.ProgressStack;
import javacake.Storage;
import javacake.Ui;
import javacake.quiz.Question;
import javacake.quiz.QuestionList;

import java.util.ArrayList;

public class QuizCommand extends Command {
    private QuestionList questionList;
    private ArrayList<Question> chosenQuestions;
    private Question.QuestionType qnType;

    /**
     * QuizCommand constructor for overall quiz.
     */
    public QuizCommand() throws DukeException {
        type = CmdType.QUIZ;
        questionList = new QuestionList();
        chosenQuestions = new ArrayList<>();
        qnType = Question.QuestionType.ALL;

        chosenQuestions = questionList.pickQuestions();
    }

    /**
     * QuizCommand constructor for topic-based quiz.
     * @param questionType the topic of the quiz.
     */
    public QuizCommand(Question.QuestionType questionType) throws DukeException {
        type = CmdType.QUIZ;
        questionList = new QuestionList();
        chosenQuestions = new ArrayList<>();
        qnType = questionType;

        chosenQuestions = questionList.pickQuestions(questionType);
    }

    /**
     * Executes the quiz.
     * @param progressStack how far the program is currently in in the table of contents.
     * @param ui the UI responsible for inputs and outputs of the program.
     * @param storage Storage to write updated data.
     * @param profile Profile of the user.
     * @throws DukeException Error thrown when there is a problem with score calculation.
     */
    @Override
    public void execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        int score = 0;
        for (int i = 0; i < QuestionList.MAX_QUESTIONS; i++) {
            Question question = chosenQuestions.get(i);
            ui.displayQuiz(question.getQuestion(), i + 1, QuestionList.MAX_QUESTIONS);
            String userAnswer = ui.readCommand();
            if (question.isAnswerCorrect(userAnswer)) {
                score++;
            }
            ui.showLine();
        }
        if (score > QuestionList.MAX_QUESTIONS) {
            throw new DukeException("Something went wrong when calculating the score:\n"
                    + "Calculated score is greater than maximum possible score.");
        }

        int topicIdx;
        switch (qnType) {
        case OOP:
            topicIdx = 0;
            break;
        case BASIC:
            topicIdx = 1;
            break;
        case EXTENSIONS:
            topicIdx = 2;
            break;
        case ALL:
            topicIdx = 3;
            break;
        default:
            throw new DukeException("Topic Idx out of bounds!");
        }

        if (score > profile.getContentMarks(topicIdx)) {
            if (score < 0.5 * QuestionList.MAX_QUESTIONS) {
                profile.setMarks(topicIdx, 1);
            } else if (score >= 0.5 && score < QuestionList.MAX_QUESTIONS) {
                profile.setMarks(topicIdx, 2);
            } else {
                profile.setMarks(topicIdx, 3);
            }
        }

        ui.displayResults(score, QuestionList.MAX_QUESTIONS);
    }
}
