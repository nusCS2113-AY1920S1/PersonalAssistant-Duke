package junittesting.quiztest;

import javacake.JavaCake;
import javacake.exceptions.CakeException;
import javacake.quiz.Question;
import javacake.quiz.QuestionDifficulty;
import javacake.quiz.QuestionList;
import javacake.quiz.QuestionType;
import javacake.quiz.QuizSession;
import javacake.ui.MainWindow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class QuizSessionTest {

    private static JavaCake javacake;
    private String response;
    private String[] topics;

    /**
     * In case it is the user's first time, name will be just set as list.
     */
    @BeforeAll
    static void setName() {
        try {
            javacake = new JavaCake();
        } catch (CakeException e) {
            System.out.println(e.getMessage());
        }
        javacake.getResponse("list");
    }

    @BeforeEach
    void init() {
        response = javacake.getResponse("list");
        topics = response.split("\\r?\\n");
    }


    @Test
    void levelsOfDifficulity() {
        for (int i = 1; i < topics.length; i++) {
            if (topics[i].contains("Test")) {
                String quizResponse = javacake.getResponse("goto " + i);
                String expectedResponse = "Here are the 3 subtopics available!\n"
                        + "1. Easy Quiz\n"
                        + "2. Medium Quiz\n"
                        + "3. Hard Quiz\n"
                        + "Key in the index to learn more about the topic or do the quiz!\n";
                assertEquals(expectedResponse, quizResponse);
            }
        }
    }

    @Test
    /**
     * This test checks if quiz session works for both illegal characters and
     * options that are not valid for the particular quiz.
     */
    void nonintInput() throws CakeException {
        for (int i = 1; i < topics.length; i++) {
            if (topics[i].contains("Test")) {
                response = javacake.getResponse("goto " + i + ".1");
                break;
            }
        }

        QuizSession quizSession = new QuizSession(QuestionType.ALL, QuestionDifficulty.EASY, false);
        response = quizSession.getQuestion(0);
        String[] illegal = {"/", "?", "<", ":", ",", "*", "|", "%d"};
        String expectedError = "OOPS!!! I'm sorry, but I don't know what that means.";
        for (int j = 0; j < illegal.length; j++) {
            response = javacake.getResponse(illegal[j]);
            assertEquals(expectedError, response);
        }


    }

    @Test
    void outOfBoundInput() throws CakeException {
        for (int i = 1; i < topics.length; i++) {
            if (topics[i].contains("Test")) {
                response = javacake.getResponse("goto " + i + ".1");
                break;
            }
        }
        String expectedError = "OOPS!!! I'm sorry, but I don't know what that means.";
        QuizSession quizSession = new QuizSession(QuestionType.ALL, QuestionDifficulty.EASY, false);
        for (int i = 0; i < QuizSession.MAX_QUESTIONS; i++) {
            response = quizSession.getQuestion(0);
            //for checking number of options//
            int numOfOptions = 1;
            while (response.contains("(" + numOfOptions + ")")) {
                numOfOptions++;
            }
            response = javacake.getResponse(Integer.toString(numOfOptions));
            assertEquals(expectedError, response);
        }
    }

    @Test
    /**
     * To test for duplicate questions within a singular quiz session
     */
    void testForDuplicates() throws CakeException {
        for (int i = 1; i < topics.length; i++) {
            if (topics[i].contains("Test")) {
                response = javacake.getResponse("goto " + i + ".1");
                break;
            }
        }
        QuizSession quizSession = new QuizSession(QuestionType.ALL, QuestionDifficulty.EASY, false);
        //check if there are the correct number of test questions
        QuestionList testQuesions = quizSession.getQuestionList();
        int expectedNumOfQns = testQuesions.getQuestionList().size();
        assertEquals(expectedNumOfQns, QuizSession.MAX_QUESTIONS);

        // check if there are any duplicate questions
        Set<Question> set = new HashSet<>(testQuesions.getQuestionList());
        assertEquals(set.size(), testQuesions.getQuestionList().size());
    }



}

