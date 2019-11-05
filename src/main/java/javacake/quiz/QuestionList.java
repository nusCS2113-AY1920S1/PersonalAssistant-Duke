package javacake.quiz;

import javacake.exceptions.CakeException;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static javacake.quiz.QuizSession.logic;

public class QuestionList {
    private ArrayList<Integer> chosenQuestionsIndex;
    private ArrayList<Question> chosenQuestions;
    /** The maximum number of questions in one session of a quiz. */
    public static final int MAX_QUESTIONS = 5;
    private QuestionType questionType;
    private String filePath;
    private int totalNumOfQns = 0;

    /**
     * Initialize a list of randomly chosen questions.
     * @param type the type of question to choose.
     * @throws CakeException when there is an error loading files.
     */
    public QuestionList(QuestionType type) throws CakeException {
        questionType = type;
        this.filePath = logic.getFullFilePath();
        totalNumOfQns = logic.getNumOfFiles();
        chosenQuestionsIndex = new ArrayList<>();
        chosenQuestions = new ArrayList<>();
        pickQuestions();
        loadQuestions();
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

    public boolean setAndCheckUserAnswer(int index, String input) throws CakeException {
        chosenQuestions.get(index).setUserAnswer(input);
        return (chosenQuestions.get(index).isAnswerCorrect(input));
    }

    /**
     * Method to get questions from current directory with indexes from chosenQuestionsIndex.
     */
    public void loadQuestions() throws CakeException {
        for (Integer i : chosenQuestionsIndex) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                i += 1;
                String fileContentPath = filePath + "/Qn" + i + ".txt";
                InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileContentPath);
                assert inputStream != null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String sentenceRead;
                while ((sentenceRead = reader.readLine()) != null) {
                    stringBuilder.append(sentenceRead).append("\n");
                }
                reader.close();
                String read = stringBuilder.toString();
                String[] questions = read.substring(0, stringBuilder.length() - 1).split("\\|\\s*");
                this.chosenQuestions.add(new Question(questions[0],
                        questions[1].trim(), Integer.parseInt(questions[2])));

            } catch (Exception e) {
                throw new CakeException("Error in loading file :(");
            }
        }
        assert chosenQuestions.size() > 0;
    }

    private void pickQuestions() {
        Random rand = new Random();

        for (int i = 0; i < MAX_QUESTIONS; i++) {
            int randomNum;
            do {
                randomNum = rand.nextInt(totalNumOfQns);
            } while (chosenQuestionsIndex.contains(randomNum)); // prevents repeat questions
            chosenQuestionsIndex.add(randomNum);
        }
        assert (chosenQuestionsIndex.size() == MAX_QUESTIONS);
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}
