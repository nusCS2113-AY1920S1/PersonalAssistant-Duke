package javacake.quiz;

import javacake.DukeException;
import javacake.ProgressStack;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class QuestionList {
    private ArrayList<Question> chosenQuestions;
    private int TOTALNUMOFBASICQNS = 0;
    private int TOTALNUMOFOOPQNS = 0;
    private int TOTALNUMOFUSEFULEXTENSIONQNS = 0;
    /** The maximum number of questions in one session of a quiz. */
    public static final int MAX_QUESTIONS = 5;

    public QuestionList() {
        chosenQuestions = new ArrayList<>(MAX_QUESTIONS);
    }


    /**
     * Updates the current number of basic questions in the hardcoded file path and returns all the questions stored.
     * @return ArrayList of all the basic questions available.
     */
    private ArrayList<BasicQuestion> initBasicList() throws DukeException {
        String filePath = "content/MainList/1. Java Basics/4. Quiz";
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i ++) {
            if (listOfFiles[i] != null) {
                TOTALNUMOFBASICQNS++;
            }
        }

        ArrayList<BasicQuestion> basicQuestionList = new ArrayList<>();
        for (int i = 1; i <= TOTALNUMOFBASICQNS; i++) {
            try {
                filePath = "content/MainList/1. Java Basics/4. Quiz/Qn" + i + ".txt";
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                String currentLine;
                String qns = new String();
                while ((currentLine = bufferedReader.readLine()) != null) {
                    qns = qns + currentLine + "\n";
                }
                qns = qns.substring(0,qns.length() - 1); // to remove the last appended new line character
                String[] questions = qns.split("\\|\\s*");
                basicQuestionList.add(new BasicQuestion(questions[0], questions[1]));
            } catch (IOException e) {
                throw new DukeException("File not found!");
            }
        }
        return basicQuestionList;
    }

    /**
     * Updates the current number of oop questions in the hardcoded file path and returns all the questions stored.
     * @return ArrayList of all the oop questions available.
     */
    private ArrayList<OopQuestion> initOopList() throws DukeException {
        String filePath = "content/MainList/2. Object-Oriented Programming/5. Quiz";
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i ++) {
            if (listOfFiles[i] != null) {
                TOTALNUMOFOOPQNS++;
            }
        }

        ArrayList<OopQuestion> oopQuestionList = new ArrayList<>();
        for (int i = 1; i <= TOTALNUMOFOOPQNS; i++) {
            try {
                filePath = "content/MainList/2. Object-Oriented Programming/5. Quiz/Qn" + i + ".txt";
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                String currentline;
                String qns = new String();

                while ((currentline = bufferedReader.readLine()) != null) {
                    qns = qns + currentline + "\n";
                }
                qns = qns.substring(0,qns.length() - 1); // to remove the last appended new line character
                String[] questions = qns.split("\\|\\s*");
                oopQuestionList.add(new OopQuestion(questions[0], questions[1]));

            } catch (IOException e) {
                throw new DukeException("File not found!");
            }
        }
        return oopQuestionList;
    }

    /**
     * Updates the current number of extension questions in the hardcoded file path and returns all the questions stored.
     * @return ArrayList of all the extension questions available.
     */
    private ArrayList<ExtensionQuestion> initExtensionList() throws DukeException {
        String filePath = "content/MainList/3. Extensions/4. Quiz";
        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i ++) {
            if (listOfFiles[i] != null) {
                TOTALNUMOFUSEFULEXTENSIONQNS++;
            }
        }

        ArrayList<ExtensionQuestion> extensionQuestionList = new ArrayList<>();
        for (int i = 1; i <= TOTALNUMOFUSEFULEXTENSIONQNS; i++) {

            try {
                filePath = "content/MainList/3. Extensions/4. Quiz/Qn" + i + ".txt";
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
                String currentLine; 
                String qns = new String();

                while ((currentLine = bufferedReader.readLine()) != null) {
                    qns = qns + currentLine + "\n";
                }
                qns = qns.substring(0,qns.length() - 1); // to remove the last appended new line character
                String[] questions = qns.split("\\|\\s*");
                extensionQuestionList.add(new ExtensionQuestion(questions[0], questions[1]));

            } catch (IOException e) {
                throw new DukeException("File not found!");
            }
        }
        return extensionQuestionList;
    }

    /**
     * Randomly selects MAX_QUESTIONS number of questions from the list of all questions.
     * @return ArrayList of Question of size MAX_QUESTIONS.
     */
    public ArrayList<Question> pickQuestions() throws DukeException {
        ArrayList<Question> allQuestions = new ArrayList<>();
        allQuestions.addAll(initBasicList());
        allQuestions.addAll(initOopList());
        allQuestions.addAll(initExtensionList());
        assert (allQuestions.size() >= MAX_QUESTIONS);

        Random rand = new Random();
        ArrayList<Integer> chosenNumbers = new ArrayList<>();

        for (int i = 0; i < MAX_QUESTIONS; i++) {
            int randomNum;
            do {
                randomNum = rand.nextInt(allQuestions.size());
            } while (chosenNumbers.contains(randomNum)); // prevents repeat questions
            chosenNumbers.add(randomNum);
            try {
                chosenQuestions.add(allQuestions.get(randomNum));
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Something went wrong when loading the quiz: index out of bounds.");
            }
        }
        return chosenQuestions;
    }

    /**
     * Randomly selects MAX_QUESTIONS number of questions of the specified topic from the list of all questions.
     * @param type QuestionType of questions to be selected.
     * @return ArrayList of Question of specified topic of size MAX_QUESTIONS.
     */
    public ArrayList<Question> pickQuestions(Question.QuestionType type) throws DukeException {
        ArrayList<Question> tempList = new ArrayList<>();
        switch (type) {
        case BASIC:
            assert (initBasicList().size() >= MAX_QUESTIONS);
            tempList.addAll(initBasicList());
            break;
        case OOP:
            assert (initOopList().size() >= MAX_QUESTIONS);
            tempList.addAll(initOopList());
            break;
        case EXTENSIONS:
            assert (initExtensionList().size() >= MAX_QUESTIONS);
            tempList.addAll(initExtensionList());
            break;
        default:
            tempList.addAll(initBasicList());
            tempList.addAll(initOopList());
            tempList.addAll(initExtensionList());
            assert (tempList.size() >= MAX_QUESTIONS);
            break;
        }

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
        return chosenQuestions;
    }
}
