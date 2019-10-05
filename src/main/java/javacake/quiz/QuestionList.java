package javacake.quiz;

import java.util.ArrayList;
import java.util.Random;

public class QuestionList {
    private ArrayList<Question> chosenQuestions;
    private int basicQuestionsTotal;
    private int oopQuestionsTotal;
    private int extensionsQuestionsTotal;
    public static final int MAX_QUESTIONS = 1;

    public QuestionList() {
        chosenQuestions = new ArrayList<>(MAX_QUESTIONS);
    }

    private ArrayList<BasicQuestion> initBasicList() {
        ArrayList<BasicQuestion> basicQuestionList = new ArrayList<>();

        String q1 = "How many members are in this team? Answer in int.";
        String a1 = "4";

        basicQuestionList.add(new BasicQuestion(q1, a1));

        return basicQuestionList;
    }

    private ArrayList<OOPQuestion> initOOPList() {
        ArrayList<OOPQuestion> OOPQuestionList = new ArrayList<>();

        String q1 = "What does OOP stand for in software engineering context? Enter the corresponding alphabet.\n" +
                " a. Out Of Print\n b. Object-Oriented Programming\n c. Ogre Onion Paradigm";
        String a1 = "b";

        OOPQuestionList.add(new OOPQuestion(q1, a1));

        return OOPQuestionList;
    }

    private ArrayList<ExtensionQuestion> initExtensionList() {
        ArrayList<ExtensionQuestion> ExtensionQuestionList = new ArrayList<>();

        String q1 = "What should you NOT do to handle an exception in main? Enter the corresponding alphabet.\n" +
                " a. Rethrow the exception\n b. Print error message \n c. Call backup method";
        String a1 = "a";

        ExtensionQuestionList.add(new ExtensionQuestion(q1,a1));

        return ExtensionQuestionList;
    }

    /**
     * Randomly selects MAX_QUESTIONS number of questions from the list of all questions.
     * @return ArrayList of Question of size MAX_QUESTIONS.
     */
    public ArrayList<Question> pickQuestions() {
        ArrayList<Question> allQuestions = new ArrayList<>();
        allQuestions.addAll(initBasicList());
        allQuestions.addAll(initOOPList());
        allQuestions.addAll(initExtensionList());
        assert(allQuestions.size() >= MAX_QUESTIONS);

        Random rand = new Random();
        ArrayList<Integer> chosenNumbers = new ArrayList<>();

        for (int i = 0; i < MAX_QUESTIONS; i++) {
            int randomNum;
            do {
                randomNum = rand.nextInt(allQuestions.size());
            } while (chosenNumbers.contains(randomNum)); // prevents repeat questions
            chosenNumbers.add(randomNum);
            chosenQuestions.add(allQuestions.get(randomNum));
        }
        return chosenQuestions;
    }

    /**
     * Randomly selects MAX_QUESTIONS number of questions of the specified type from the list of all questions.
     * @param type QuestionType of questions to be selected.
     * @return ArrayList of Question of specified type of size MAX_QUESTIONS.
     */
    public ArrayList<Question> pickQuestions(Question.QuestionType type) {
        ArrayList<Question> tempList = new ArrayList<>();
        switch (type) {
        case BASIC:
            assert (initBasicList().size() >= MAX_QUESTIONS);
            tempList.addAll(initBasicList());
            break;
        case OOP:
            assert (initOOPList().size() >= MAX_QUESTIONS);
            tempList.addAll(initOOPList());
            break;
        case EXTENSIONS:
            assert (initExtensionList().size() >= MAX_QUESTIONS);
            tempList.addAll(initExtensionList());
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
            chosenQuestions.add(tempList.get(randomNum));
        }
        return chosenQuestions;
    }
}
