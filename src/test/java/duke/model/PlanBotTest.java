package duke.model;

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
        planBot.processInput("yes");
        int newSize = planBot.getDialogObservableList().size();
        Assertions.assertTrue(newSize > currSize);
        knownAttributes.put("NUS_STUDENT", "TRUE");
        Assertions.assertEquals(planBot.getPlanAttributes(), knownAttributes);
    }
}
