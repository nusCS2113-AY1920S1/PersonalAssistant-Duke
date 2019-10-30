package javacake.quiz;

import javacake.exceptions.DukeException;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static javacake.quiz.QuizSession.logic;

public class QuestionList {
    private ArrayList<Question> tempList;
    private ArrayList<Question> chosenQuestions;
    /** The maximum number of questions in one session of a quiz. */
    public static final int MAX_QUESTIONS = 5;
    private QuestionType questionType;
    private String filePath;
    private int totalNumOfQns = 0;

    /**
     * Initialize a list of randomly chosen questions.
     * @param type the type of question to choose.
     * @throws DukeException when there is an error loading files.
     */
    public QuestionList(QuestionType type) throws DukeException {
        questionType = type;
        this.filePath = logic.getFullFilePath();
        totalNumOfQns = logic.getNumOfFiles();
        tempList = new ArrayList<>();
        loadQuestions();
        chosenQuestions = pickQuestions();
    }

    public ArrayList<Question> getQuestionList() {
        return chosenQuestions;
    }

    public String getQuestion(int index) {
        return index + 1 + "/" + MAX_QUESTIONS + "\n" + chosenQuestions.get(index).getQuestion();
    }

    public String getAnswers(int index) {
        return "Your answer: " + chosenQuestions.get(index).getUserAnswer()
                + "\nCorrect answer: " + chosenQuestions.get(index).getAnswer();
    }

    public boolean setAndCheckUserAnswer(int index, String input) {
        chosenQuestions.get(index).setUserAnswer(input);
        return (chosenQuestions.get(index).isAnswerCorrect(input));
    }

    /**
     * Method to get all questions in the given directory.
     */
    public void loadQuestions() throws DukeException {
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
                this.tempList.add(new Question(questions[0], questions[1]));
            } catch (Exception e) {
                throw new DukeException("Error in loading file :(");
            }
        }
        assert tempList.size() > 0;
    }

    /**
     * Randomly selects MAX_QUESTIONS number of questions of the specified topic from the list of all questions.
     * @return ArrayList of Question of specified topic of size MAX_QUESTIONS.
     */
    public ArrayList<Question> pickQuestions() {
        Random rand = new Random();
        ArrayList<Integer> chosenNumbers = new ArrayList<>();
        ArrayList<Question> tempList2 = new ArrayList<>();

        for (int i = 0; i < MAX_QUESTIONS; i++) {
            int randomNum;
            do {
                randomNum = rand.nextInt(tempList.size());
            } while (chosenNumbers.contains(randomNum)); // prevents repeat questions
            chosenNumbers.add(randomNum);
            tempList2.add(tempList.get(randomNum));
        }
        assert (tempList2.size() == MAX_QUESTIONS);
        return tempList2;
    }
}
