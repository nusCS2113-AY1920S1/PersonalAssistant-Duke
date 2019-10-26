package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlanQuestionTest {
    @Test
    public void TestConstruction(){
        try {
            PlanQuestion question = new PlanQuestion("TestQuestion",new String[]{"YES", "NO"}, new String[]{"TRUE","FALSE"},"TEST" );
            Assertions.assertEquals(question.getQuestion(), "TestQuestion");
            Assertions.assertEquals(question.getAttribute(), "TEST");
        } catch (DukeException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
