package javacake.commands;

import javacake.Duke;
import javacake.Logic;
import javacake.Parser;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.StorageManager;
import javacake.ui.TopBar;
import javacake.ui.Ui;
import javacake.quiz.Question;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class QuizCommand extends Command {
    public static final int MAX_QUESTIONS = 5;
    //private QuestionList questionList;
    public ArrayList<Question> chosenQuestions;
    public ArrayList<Question> questionList = new ArrayList<>();
    public String filePath;
    private Question.QuestionType qnType;
    private Question.QuestionDifficulty qnDifficulty;
    private Question prevQuestion;
    public static int TotalMaxQuestions = 15;
    public int questionCounter = MAX_QUESTIONS - 1;
    private int currScore = 0;
    private static Profile profile;
    public ScoreGrade scoreGrade;
    int totalNumOfQns = 0;
    public static Logic logic = Logic.getInstance();

    public enum ScoreGrade {
        BAD, OKAY, GOOD
    }


    /**
     * QuizCommand constructor for topic-based quiz.
     * @param questionType the topic of the quiz.
     */
    public QuizCommand(Question.QuestionType questionType, Question.QuestionDifficulty questionDifficulty,
                       Boolean isCli) throws DukeException {
        qnDifficulty = questionDifficulty;
        type = CmdType.QUIZ;
        chosenQuestions = new ArrayList<>();
        qnType = questionType;
        if (!isCli) {
            this.filePath = logic.getFullFilePath();
            System.out.println(this.filePath);
            runGui();
        }
    }

    public static void setProfile(Profile profile) {
        QuizCommand.profile = profile;
    }

    /**
     * Method to get all questions in the given directory.
     */
    public void getQuestions() throws DukeException {


        for (int i = 1; i <= totalNumOfQns; i++) {
            try {
                String fileContentPath = filePath + "/Qn" + i + ".txt";
                InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileContentPath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String sentenceRead;
                while ((sentenceRead = reader.readLine()) != null) {
                    stringBuilder.append(sentenceRead).append("\n");
                }
                reader.close();
                String[] questions = stringBuilder.toString().substring(0,stringBuilder.length() - 1).split("\\|\\s*");
                this.questionList.add(new Question(questions[0], questions[1]));
            } catch (Exception e) {
                throw new DukeException("Error in loading file :(");
            }
        }

    }



    /**
     * Randomly selects MAX_QUESTIONS number of questions of the specified topic from the list of all questions..
     */
    public void pickQuestions() throws DukeException {
        ArrayList<Question> tempList = questionList;
        Random rand = new Random();
        ArrayList<Integer> chosenNumbers = new ArrayList<>();

        for (int i = 0; i < MAX_QUESTIONS; i++) {
            int randomNum;
            do {
                randomNum = rand.nextInt(tempList.size());
            } while (chosenNumbers.contains(randomNum)); // prevents repeat questions
            chosenNumbers.add(randomNum);
            try {
                chosenQuestions.add(tempList.get(randomNum));
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Something went wrong when loading the quiz: index out of bounds.");
            }
        }
    }

    /**
     * Executes the quiz.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws DukeException Error thrown when there is a problem with score calculation.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        logic.insertQueries();
        assert !logic.containsDirectory();
        this.filePath = logic.getFullFilePath();
        totalNumOfQns = logic.getNumOfFiles();
        getQuestions();
        pickQuestions();
        for (int i = 0; i < MAX_QUESTIONS; i++) {
            Question question = chosenQuestions.get(i);
            ui.displayQuiz(question.getQuestion(), i + 1, MAX_QUESTIONS);
            String userAnswer = ui.readCommand();
            chosenQuestions.get(i).setUserAnswer(userAnswer);
            if (question.isAnswerCorrect(userAnswer)) {
                currScore++;
            }
            ui.showLine();
        }
        if (currScore > MAX_QUESTIONS) {
            throw new DukeException("Something went wrong when calculating the score:\n"
                    + "Calculated score is greater than maximum possible score.");
        }

        overwriteOldScore(currScore, profile);

        ui.displayResults(currScore, MAX_QUESTIONS);
        String nextCommand = ui.readCommand();
        if (nextCommand.equals("review")) {
            return new ReviewCommand(chosenQuestions).execute(logic, ui, storageManager);
        } else {
            Command newCommand = Parser.parse(nextCommand);
            return newCommand.execute(logic, ui, storageManager);
        }
    }

    /**
     * Method to execute but for GUI.
     */

    public void runGui() throws DukeException {
        totalNumOfQns = logic.getNumOfFiles();
        getQuestions();
        pickQuestions();
    }

    /**
     * Method to overwrite the old totalScore of user,
     * if it's less than the current totalScore.
     * @param totalScore new totalScore of user
     * @param profile profile object of user
     * @throws DukeException error if question type is undefined
     */
    public void overwriteOldScore(int totalScore, Profile profile) throws DukeException {
        int individualtopicIdx;
        int overalltopicIdx;
        switch (qnType) {
        case BASIC:
            overalltopicIdx = 0;
            break;
        case OOP:
            overalltopicIdx = 1;
            break;
        case EXTENSIONS:
            overalltopicIdx = 2;
            break;
        case ALL:
            overalltopicIdx = 3;
            break;
        default:
            throw new DukeException("Topic Idx out of bounds!");
        }

        switch (qnDifficulty) {
        case EASY:
            individualtopicIdx = overalltopicIdx * 3;
            break;
        case MEDIUM:
            individualtopicIdx = overalltopicIdx * 3 + 1;
            break;
        case HARD:
            individualtopicIdx = overalltopicIdx * 3 + 2;
            break;
        default:
            throw new DukeException("Topic Idx out of bounds!");
        }

        if (totalScore > profile.getIndividualContentMarks(individualtopicIdx)) {
            profile.setIndividualMarks(individualtopicIdx,totalScore);
            if (individualtopicIdx % 3 == 0) {
                totalScore += profile.getIndividualContentMarks(individualtopicIdx + 1);
                totalScore += profile.getIndividualContentMarks(individualtopicIdx + 2);
            } else if (individualtopicIdx % 3 == 1) {
                totalScore += profile.getIndividualContentMarks(individualtopicIdx + 1);
                totalScore += profile.getIndividualContentMarks(individualtopicIdx - 1);
            } else if (individualtopicIdx % 3 == 2) {
                totalScore += profile.getIndividualContentMarks(individualtopicIdx - 2);
                totalScore += profile.getIndividualContentMarks(individualtopicIdx - 1);
            }

            profile.setOverallMarks(overalltopicIdx, totalScore);

            if (!Duke.isCliMode()) {
                switch (overalltopicIdx) {
                case 0:
                    Duke.logger.log(Level.INFO, totalScore + " YEET");
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
            }
        }
    }

    /**
     * Method to get the next Question.
     * @return the string containing the next question
     */
    public String getNextQuestion() {
        prevQuestion = chosenQuestions.get(questionCounter);
        questionCounter--;
        return prevQuestion.getQuestion();
    }

    /**
     * Method to check if answer is correct.
     * If it is, then update the score.
     * @param input the answer inputted by the user
     * @throws DukeException error thrown if user inputs wrong type of answer.
     */
    public void checkAnswer(String input) throws DukeException {
        if (!isNumeric(input)) {
            throw new DukeException("Please input answers in the form of a positive integer");
        }
        if (prevQuestion.isAnswerCorrect(input)) {
            currScore++;
        }
    }

    private static boolean isNumeric(String input) {
        try {
            int integer = Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Method to get the score of the quiz.
     * @return String containing what Cake said about the quiz.
     * @throws DukeException error thrown if failed to overwrite score.
     */
    public String getQuizScore() throws DukeException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This is your score:");
        stringBuilder.append("    ").append(currScore).append(" / ").append(MAX_QUESTIONS).append("\n");

        if ((double)currScore / MAX_QUESTIONS <= 0.5) {
            stringBuilder.append("Aw, that's too bad! Try revising the topics and try again. Don't give up!");
            scoreGrade = ScoreGrade.BAD;
        } else if ((double)currScore / MAX_QUESTIONS != 1.0) {
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
