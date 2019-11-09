package duke.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class PlanBotTest {
    @Test
    public void testPositive() {
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

    @Test
    public void testInvalidInput() {
        Map<String, String> knownAttributes = new HashMap<>();
        PlanBot planBot = new PlanBot(knownAttributes);
        Assertions.assertNull(planBot.getPlanBudgetRecommendation());
        planBot.processInput("random string of inputs");
        PlanBot.PlanDialog planDialog = new PlanBot.PlanDialog("Please enter a valid reply!", PlanBot.Agent.BOT);
        int size = planBot.getDialogObservableList().size();
        PlanBot.PlanDialog planDialog1 = planBot.getDialogObservableList().get(size - 1);
        Assertions.assertEquals(planDialog.text, planDialog1.text);
        Assertions.assertEquals(planDialog.agent, planDialog1.agent);
    }


}
