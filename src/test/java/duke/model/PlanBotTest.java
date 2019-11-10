package duke.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


public class PlanBotTest {
    @Test
    public void testPositive() {
        Map<String, String> knownAttributes = new HashMap<>();
        PlanBot planBot = PlanBot.getInstance(knownAttributes);
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
        PlanBot planBot = PlanBot.getInstance(knownAttributes);
        Assertions.assertNull(planBot.getPlanBudgetRecommendation());
        planBot.processInput("random string of inputs");
        PlanBot.PlanDialog planDialogExpected1 = new PlanBot.PlanDialog("Please enter a valid reply!",
                PlanBot.Agent.BOT);
        PlanBot.PlanDialog planDialogExpected2 = new PlanBot.PlanDialog(
                "random string of inputs is not a valid amount!", PlanBot.Agent.BOT);
        int size = planBot.getDialogObservableList().size();
        PlanBot.PlanDialog planDialog = planBot.getDialogObservableList().get(size - 1);
        Assertions.assertTrue(planDialogExpected1.text.equals(planDialog.text)
                || planDialogExpected2.text.equals(planDialog.text));
    }


}
