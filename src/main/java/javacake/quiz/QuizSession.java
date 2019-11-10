package javacake.quiz;

import javacake.JavaCake;
import javacake.Logic;
import javacake.Parser;
import javacake.commands.Command;
import javacake.exceptions.CakeException;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.ui.TopBar;
import javacake.ui.Ui;

import java.util.logging.Level;

public class QuizSession implements QuizManager {
    private QuestionList questionList;
    public String filePath;
    private QuestionType qnType;
    private QuestionDifficulty qnDifficulty;
    private int currScore = 0;
    private static Profile profile;
    public ScoreGrade scoreGrade;
    public int totalNumOfQns = 0;
    public static Logic logic = Logic.getInstance();
    private boolean isQuizComplete;
    public static final int MAX_QUESTIONS = 5;
    public static final double PERCENTAGE_1 = 0.5;
    public static final double PERCENTAGE_2 = 1.0;
    public static final int levelsOfDifficulty = 3;
    public static int TotalMaxQuestions = MAX_QUESTIONS * levelsOfDifficulty;

    public enum ScoreGrade {
        BAD, OKAY, GOOD
    }

    /**
     * QuizCommand constructor for topic-based quiz.
     * @param questionType the topic of the quiz.
     */
    public QuizSession(QuestionType questionType, QuestionDifficulty questionDifficulty, boolean isCli)
            throws CakeException {
        questionList = new QuestionList(questionType);
        qnType = questionType;
        qnDifficulty = questionDifficulty;
        if (!isCli) {
            runGui();
        }
        isQuizComplete = false;
        JavaCake.logger.log(Level.INFO, "initialize quiz session: " + qnType + ", " + qnDifficulty);
    }

    /**
     * Method to get the question string.
     * @param index question index between 0 and MAX_QUESTIONS-1.
     * @return question string.
     */
    @Override
    public String getQuestion(int index) {
        JavaCake.logger.log(Level.INFO,"Question " + index + ":\n" + questionList.getQuestion(index));
        return questionList.getQuestion(index);
    }

    /**
     * Parses valid user input for quiz session.
     * @param index question index between 0 and MAX_QUESTIONS-1.
     * @param input user input, either as user's answer or commands available on results page.
     * @return command identifier if on result page, null otherwise.
     * @throws CakeException if user answer is not integer value during quiz, or command is invalid on results page.
     */
    @Override
    public String parseInput(int index, String input) throws CakeException {
        if (isQuizComplete) {
            switch (input) {
            case ("review"):
                JavaCake.logger.log(Level.INFO, "User chose to REVIEW");
                return "!@#_REVIEW";
            case ("back"):
                JavaCake.logger.log(Level.INFO, "User chose to go BACK");
                return "!@#_BACK";
            default:
                JavaCake.logger.log(Level.WARNING, "User entered invalid command: " + input);
                throw new CakeException("[!] Invalid command at this point in the program [!]\n"
                        + "    Try \"review\" or \"back\"."
                        + "\n\n" + getQuizResult());
            }
        } else {
            try {
                checkAnswer(index, input);
            } catch (CakeException e) {
                throw new CakeException(e.getMessage() + getQuestion(index));
            }
            return null;
        }
    }

    public QuestionList getQuestionList() {
        return questionList;
    }

    public static void setProfile(Profile profile) {
        QuizSession.profile = profile;
    }

    /**
     * Executes the quiz.
     * @param logic TaskList containing current tasks
     * @param ui the UI responsible for inputs and outputs of the program.
     * @param storageManager storage container.
     * @return execution of next command from input at results screen.
     * @throws CakeException Error thrown when there is a problem with score calculation.
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        logic.insertQueries();
        assert !logic.containsDirectory();
        this.filePath = logic.getFullFilePath();
        totalNumOfQns = logic.getNumOfFiles();
        for (int i = 0; i < MAX_QUESTIONS; i++) {
            Question question = questionList.getQuestionList().get(i);
            ui.displayQuiz(question.getQuestion(), i + 1, MAX_QUESTIONS);
            String userAnswer = ui.readCommand();
            questionList.getQuestionList().get(i).setUserAnswer(userAnswer);
            if (question.isAnswerCorrect(userAnswer)) {
                currScore++;
            }
            ui.showLine();
        }
        if (currScore > MAX_QUESTIONS) {
            throw new CakeException("Something went wrong when calculating the score:\n"
                    + "Calculated score is greater than maximum possible score.");
        }

        overwriteOldScore(currScore, profile);

        ui.displayResults(currScore, MAX_QUESTIONS);
        String nextCommand = ui.readCommand();
        if (nextCommand.equals("review")) {
            return new ReviewSession(questionList).execute(logic, ui, storageManager);
        } else {
            Command newCommand = Parser.parse(nextCommand);
            return newCommand.execute(logic, ui, storageManager);
        }
    }

    /**
     * Method to execute but for GUI.
     */
    public void runGui() throws CakeException {
        totalNumOfQns = logic.getNumOfFiles();
    }

    /**
     * Method to overwrite the old totalScore of user,
     * if it's less than the current totalScore.
     * @param totalScore new totalScore of user
     * @param profile profile object of user
     * @throws CakeException error if question type is undefined
     */
    public void overwriteOldScore(int totalScore, Profile profile) throws CakeException {
        int individualTopicIdx;
        int overallTopicIdx;
        switch (qnType) {
        case BASIC:
            overallTopicIdx = 0;
            break;
        case OOP:
            overallTopicIdx = 1;
            break;
        case EXTENSIONS:
            overallTopicIdx = 2;
            break;
        case ALL:
            overallTopicIdx = 3;
            break;
        default:
            throw new CakeException("Topic Idx out of bounds!");
        }

        switch (qnDifficulty) {
        case EASY:
            individualTopicIdx = overallTopicIdx * levelsOfDifficulty;
            break;
        case MEDIUM:
            individualTopicIdx = overallTopicIdx * levelsOfDifficulty + 1;
            break;
        case HARD:
            individualTopicIdx = overallTopicIdx * levelsOfDifficulty + 2;
            break;
        default:
            throw new CakeException("Topic Idx out of bounds!");
        }

        if (totalScore > profile.getIndividualContentMarks(individualTopicIdx)) {
            profile.setIndividualMarks(individualTopicIdx,totalScore);
            if (individualTopicIdx % levelsOfDifficulty == 0) {
                totalScore += profile.getIndividualContentMarks(individualTopicIdx + 1);
                totalScore += profile.getIndividualContentMarks(individualTopicIdx + 2);
            } else if (individualTopicIdx % levelsOfDifficulty == 1) {
                totalScore += profile.getIndividualContentMarks(individualTopicIdx + 1);
                totalScore += profile.getIndividualContentMarks(individualTopicIdx - 1);
            } else if (individualTopicIdx % levelsOfDifficulty == 2) {
                totalScore += profile.getIndividualContentMarks(individualTopicIdx - 2);
                totalScore += profile.getIndividualContentMarks(individualTopicIdx - 1);
            }

            profile.setOverallMarks(overallTopicIdx, totalScore);

            if (!profile.isCli) {
                switch (overallTopicIdx) {
                case 0:
                    TopBar.progValueA = (double) totalScore / TotalMaxQuestions;
                    break;
                case 1:
                    TopBar.progValueB = (double) totalScore / TotalMaxQuestions;
                    break;
                case 2:
                    TopBar.progValueC = (double) totalScore / TotalMaxQuestions;
                    break;
                case 3:
                    TopBar.progValueD = (double) totalScore / TotalMaxQuestions;
                    break;
                default:
                }
                TopBar.progValueT = (double) profile.getTotalProgress() / (TotalMaxQuestions * 4);

                JavaCake.logger.log(Level.INFO, "user score updated in profile");
            }
        }
    }

    /**
     * Method to set user answer and check if answer is correct.
     * If it is, then update the score.
     * @param input the answer inputted by the user
     * @throws CakeException error thrown if user inputs wrong type of answer.
     */
    private void checkAnswer(int index, String input) throws CakeException {
        if (!isNumeric(input)) {
            String userWarning = "[!] Please input answers in the form of a valid integer [!]\n";
            JavaCake.logger.log(Level.WARNING, "User entered non-valid input");
            throw new CakeException(userWarning);
        }
        if (questionList.setAndCheckUserAnswer(index, input)) {
            currScore++;
        }
    }

    private static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Method to get the results of the quiz.
     * @return String containing what Cake said about the quiz.
     * @throws CakeException error thrown if failed to overwrite score.
     */
    public String getQuizResult() throws CakeException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This is your score:");
        stringBuilder.append("    ").append(currScore).append(" / ").append(MAX_QUESTIONS).append("\n");

        if ((double)currScore / MAX_QUESTIONS <= PERCENTAGE_1) {
            stringBuilder.append("Aw, that's too bad! Try revising the topics and try again. Don't give up!");
            scoreGrade = ScoreGrade.BAD;
        } else if ((double)currScore / MAX_QUESTIONS != PERCENTAGE_2) {
            stringBuilder.append("Almost there! Clarify some of your doubts and try again.");
            scoreGrade = ScoreGrade.OKAY;
        } else {
            stringBuilder.append("Congrats! Full marks, you're amazing!");
            scoreGrade = ScoreGrade.GOOD;
        }

        stringBuilder.append("\nType \"review\" to review your answers.");
        stringBuilder.append("\nType \"back\" to go back to the table of contents.");

        JavaCake.logger.log(Level.INFO, "Quiz result\nScore: " + currScore + "ScoreGrade: " + scoreGrade);

        overwriteOldScore(currScore, profile);
        isQuizComplete = true;

        return stringBuilder.toString();
    }
}
