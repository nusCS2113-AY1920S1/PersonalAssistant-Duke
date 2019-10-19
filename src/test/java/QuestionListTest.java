import javacake.exceptions.DukeException;
import javacake.quiz.BasicQuestion;
import javacake.quiz.Question;
import javacake.quiz.QuestionList;
import org.junit.jupiter.api.Test;

import static javacake.quiz.QuestionList.MAX_QUESTIONS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuestionListTest {
    @Test
    public void initializeBasicQuizTest() throws DukeException {
        QuestionList test = new QuestionList();
        ArrayList<Question> testQuestions;
        testQuestions = test.pickQuestions(Question.QuestionType.BASIC);

        // check if there are the correct number and type of test questions
        assertEquals(testQuestions.size(), MAX_QUESTIONS);
        for (Question qn : testQuestions) {
            assertEquals(qn.getClass(), BasicQuestion.class);
        }

        // check if there are any duplicate questions
        Set<Question> set = new HashSet<>(testQuestions);
        assertEquals(set.size(), testQuestions.size());
    }
}
