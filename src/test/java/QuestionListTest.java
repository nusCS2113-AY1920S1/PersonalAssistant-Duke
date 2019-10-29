import javacake.commands.QuizCommand;
import javacake.exceptions.DukeException;
import javacake.quiz.BasicQuestion;
import javacake.quiz.Question;
import javacake.quiz.QuestionList;
import org.junit.jupiter.api.Test;

import static javacake.commands.QuizCommand.MAX_QUESTIONS;
import static javacake.commands.QuizCommand.TotalMaxQuestions;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class QuestionListTest {
    @Test
    public void initializeBasicQuizTest() throws DukeException {
        QuizCommand quiz = new QuizCommand(Question.QuestionType.BASIC, Question.QuestionDifficulty.EASY, true);
        quiz.filePath = "content/MainList/1. Java Basics/4. Test Yourself!/1. Easy Quiz";
        quiz.totalNumOfQns = 5;
        quiz.getQuestions();
        quiz.pickQuestions();
        ArrayList<Question> testQuestions = quiz.chosenQuestions;

        // check if there are the correct number and type of test questions
        assertEquals(testQuestions.size(), MAX_QUESTIONS);
       

        // check if there are any duplicate questions
        Set<Question> set = new HashSet<>(testQuestions);
        assertEquals(set.size(), testQuestions.size());
    }
}
