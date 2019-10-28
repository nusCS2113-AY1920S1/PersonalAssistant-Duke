package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PlanBotTest {
    @Test
    public void PlanBotTest() {
        Map<String, String> knownAttributes = new HashMap<>();
        PlanBot planBot = new PlanBot(knownAttributes);
        Assertions.assertNotNull(planBot.getDialogObservableList());
        Assertions.assertFalse(planBot.getDialogObservableList().isEmpty());
        int currSize = planBot.getDialogObservableList().size();
        try {
            planBot.processInput("yes");
            int newSize = planBot.getDialogObservableList().size();
            Assertions.assertTrue(newSize > currSize);
        } catch (DukeException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
