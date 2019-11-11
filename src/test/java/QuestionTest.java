import javacake.exceptions.CakeException;
import javacake.quiz.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {
    Question dummyQuestion1 = new Question("abc", "3", 5);

    @Test
    public void inputValidationPositiveTest() throws CakeException {
        assertTrue(dummyQuestion1.isAnswerCorrect(" 3   "));
    }

    @Test
    public void inputValidationNegativeTest() throws CakeException {
        assertFalse(dummyQuestion1.isAnswerCorrect("2"));
        assertFalse(dummyQuestion1.isAnswerCorrect("5  "));
    }

    @Test
    public void inputValidationNotInRangeTest() {
        Exception e1 = assertThrows(CakeException.class,
                ()-> dummyQuestion1.isAnswerCorrect("0"));
        assertEquals(e1.getMessage(),
                "[!] Please enter option number between 1 and 5! [!]\n");
        assertThrows(CakeException.class, ()-> dummyQuestion1.isAnswerCorrect("6"));
    }
}
