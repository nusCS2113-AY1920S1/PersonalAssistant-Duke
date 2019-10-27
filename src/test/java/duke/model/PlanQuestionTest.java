package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlanQuestionTest {
    @Test
    public void testConstruction() {
        try {
            PlanQuestion question = new PlanQuestion("TestQuestion",
                    new String[]{"YES", "NO"},
                    new String[]{"TRUE", "FALSE"},
                    "TEST");
            Assertions.assertEquals(question.getQuestion(), "TestQuestion");
            Assertions.assertEquals(question.getAttribute(), "TEST");
        } catch (DukeException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testQueries() {
        try {
            PlanQuestion question1 = new PlanQuestion("TestQuestion",
                    new String[]{"YES", "NO"},
                    new String[]{"TRUE", "FALSE"},
                    "TEST");
            question1.getNeighbouringQuestions("MAYBE");
            Assertions.fail();

        } catch (DukeException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
