package javacake.commands;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.ProgressStack;
import javacake.storage.Storage;
import javacake.ui.TopBar;
import javacake.ui.Ui;
import javacake.quiz.Question;
import javacake.quiz.QuestionList;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizCommand extends Command {
    private QuestionList questionList;
    public ArrayList<Question> chosenQuestions;
    private Question.QuestionType qnType;
    private Question prevQuestion;
    private int currScore = 0;
    private static Profile profile;
    public ScoreGrade scoreGrade;

    public enum ScoreGrade {
        BAD, OKAY, GOOD
    }

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

    public static void setProfile(Profile profile) {
        QuizCommand.profile = profile;
    }

    /**
     * Executes the quiz.
     * @param progressStack how far the program is currently in in the table of contents.
     * @param ui the UI responsible for inputs and outputs of the program.
     * @param storage Storage to write updated data.
     * @param profile Profile of the user.
     * @throws DukeException Error thrown when there is a problem with score calculation.
     * @return
     */
    @Override
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        progressStack.insertQueries();
        assert !progressStack.containsDirectory();
        for (int i = 0; i < QuestionList.MAX_QUESTIONS; i++) {
            Question question = chosenQuestions.get(i);
            ui.displayQuiz(question.getQuestion(), i + 1, QuestionList.MAX_QUESTIONS);
            String userAnswer = ui.readCommand();
            if (question.isAnswerCorrect(userAnswer)) {
                currScore++;
            }
            ui.showLine();
        }
        if (currScore > QuestionList.MAX_QUESTIONS) {
            throw new DukeException("Something went wrong when calculating the score:\n"
                    + "Calculated score is greater than maximum possible score.");
        }

        overwriteOldScore(currScore, profile);

        ui.displayResults(currScore, QuestionList.MAX_QUESTIONS);
        return "";
    }

    /**
     * Method to overwrite the old score of user,
     * if it's less than the current score.
     * @param score new score of user
     * @param profile profile object of user
     * @throws DukeException error if question type is undefined
     */
    public void overwriteOldScore(int score, Profile profile) throws DukeException {
        int topicIdx;
        switch (qnType) {
        case BASIC:
            topicIdx = 0;
            break;
        case OOP:
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
            profile.setMarks(topicIdx, score);
            if (!Duke.isCliMode()) {
                switch (topicIdx) {
                case 0:
                    Duke.logger.log(Level.INFO, score + " YEET");
                    TopBar.progValueA = (double) score / QuestionList.MAX_QUESTIONS;
                    break;
                case 1:
                    TopBar.progValueB = (double) score / QuestionList.MAX_QUESTIONS;
                    break;
                case 2:
                    TopBar.progValueC = (double) score / QuestionList.MAX_QUESTIONS;
                    break;
                case 3:
                    TopBar.progValueD = (double) score / QuestionList.MAX_QUESTIONS;
                    break;

                default:
                }
                TopBar.progValueT = (double) profile.getTotalProgress() / (QuestionList.MAX_QUESTIONS * 4);
            }
        }
    }

    /**
     * Method to get the next Question.
     * @return the string containing the next question
     */
    public String getQuestion() {
        prevQuestion = chosenQuestions.get(chosenQuestions.size() - 1);
        chosenQuestions.remove(chosenQuestions.size() - 1);
        return prevQuestion.getQuestion();
    }

    /**
     * Method to check if answer is correct.
     * If it is, then update the score.
     * @param input the answer inputted by the user
     */
    public void checkAnswer(String input) {
        if (prevQuestion.isAnswerCorrect(input)) {
            currScore++;
        }
    }

    /**
     * Method to get the score of the quiz.
     * @return String containing what Cake said about the quiz.
     * @throws DukeException error thrown if failed to overwrite score.
     */
    public String getQuizScore() throws DukeException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This is your score:");
        stringBuilder.append("    ").append(currScore).append(" / ").append(QuestionList.MAX_QUESTIONS).append("\n");

        if ((double)currScore / QuestionList.MAX_QUESTIONS <= 0.5) {
            stringBuilder.append("Aw, that's too bad! Try revising the topics and try again. Don't give up!");
            scoreGrade = ScoreGrade.BAD;
        } else if ((double)currScore / QuestionList.MAX_QUESTIONS != 1.0) {
            stringBuilder.append("Almost there! Clarify some of your doubts and try again.");
            scoreGrade = ScoreGrade.OKAY;
        } else {
            stringBuilder.append("Congrats! Full marks, you're amazing!");
            scoreGrade = ScoreGrade.GOOD;
        }
        stringBuilder.append("\nType \"back\" to go back to the table of contents.");

        overwriteOldScore(currScore, profile);

        return stringBuilder.toString();
    }
}
