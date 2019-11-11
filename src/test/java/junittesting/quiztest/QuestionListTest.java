package junittesting.quiztest;

import javacake.exceptions.CakeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionListTest {

    // unused, this test has been moved to QuizSessionTest.testForDuplicates()
    @Test
    public void initializeBasicQuizTest() throws CakeException {
        /*
        QuestionList test = new QuestionList(QuestionType.BASIC);

        //check if there are the correct number of test questions
        assertEquals(test.getQuestionList().size(), MAX_QUESTIONS);

        // check if there are any duplicate questions
        Set<Question> set = new HashSet<>(test.getQuestionList());
        assertEquals(set.size(), test.getQuestionList().size());
         */
    }
}
