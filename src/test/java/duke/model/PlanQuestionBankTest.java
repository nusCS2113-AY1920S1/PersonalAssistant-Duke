package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PlanQuestionBankTest {
    @Test
    public void testConstruction() {
        try {
            Map<String, String> knownQuestions = new HashMap<>();
            PlanQuestionBank planQuestionBank = new PlanQuestionBank();
            Assertions.assertFalse(planQuestionBank.getQuestions(knownQuestions).isEmpty());
        } catch (DukeException e) {
            Assertions.fail(e.getMessage());
        }
    }
    
}
