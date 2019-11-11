package junittesting.quiztest;

import javacake.JavaCake;
import javacake.exceptions.CakeException;
import javacake.quiz.QuestionDifficulty;
import javacake.quiz.QuestionType;
import javacake.quiz.QuizSession;
import javacake.quiz.ReviewSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static javacake.quiz.QuizSession.MAX_QUESTIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ReviewSessionTest {
    private ReviewSession reviewSession;
    private static JavaCake javacake;
    private String response;
    private String[] topics;

    /**
     * In case it is the user's first time, name will be just set as list.
     */
    @BeforeEach
    void init() throws CakeException {
        try {
            javacake = new JavaCake();
        } catch (CakeException e) {
            System.out.println(e.getMessage());
        }
        javacake.getResponse("list");
        response = javacake.getResponse("list");
        topics = response.split("\\r?\\n");
        for (int i = 1; i < topics.length; i++) {
            if (topics[i].contains("Test")) {
                response = javacake.getResponse("goto " + i + ".1");
                break;
            }
        }
        QuizSession quizSession = new QuizSession(QuestionType.BASIC, QuestionDifficulty.EASY, false);
        for (int i = 0; i < MAX_QUESTIONS; i++) {
            quizSession.parseInput(i, "1");
        }
        reviewSession = new ReviewSession(quizSession.getQuestionList());
    }

    @Test
    void parseInputTest() throws CakeException {
        String result;
        // positive test: go to question
        result = reviewSession.parseInput(0, "1");
        assertEquals(result, "0");

        // negative test: non-int string
        Exception e1 = assertThrows(CakeException.class, () -> reviewSession.parseInput(0, "no"));
        assertTrue(e1.getMessage().contains("You can't use that command here"));

        // negative test: out of bounds of number of questions
        Exception e2 = assertThrows(CakeException.class,
                () -> reviewSession.parseInput(0, String.valueOf(MAX_QUESTIONS + 1)));
        assertTrue(e2.getMessage().contains("That question number is out of range! Try again."));

        // negative test: int overflow input
        Exception e3 = assertThrows(CakeException.class,
                () -> reviewSession.parseInput(0, "2147483700"));
        assertTrue(e3.getMessage().contains("number is out of range!"));

        // positive test: back
        assertEquals(reviewSession.parseInput(0,"back"), "!@#_BACK");
    }
}
