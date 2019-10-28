package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PlanQuestionBankTest {
    @Test
    public void testPlanQuestionBank() {
        try {
            Map<String, String> knownAttributes = new HashMap<>();
            PlanQuestionBank planQuestionBank = new PlanQuestionBank();
            Assertions.assertFalse(planQuestionBank.getQuestions(knownAttributes).isEmpty());
            Queue<PlanQuestion> questionQueue = new LinkedList<>();
            questionQueue.addAll(planQuestionBank.getQuestions(knownAttributes));
            Assertions.assertFalse(questionQueue.isEmpty());
            PlanQuestion firstQuestion = questionQueue.peek();
            questionQueue.remove();
            Assertions.assertEquals(firstQuestion.getQuestion(), "Are you a student from NUS? <yes/no>");
            PlanQuestion.Reply reply = firstQuestion.getReply("yes", knownAttributes);
            knownAttributes = reply.getAttributes();
            questionQueue.addAll(planQuestionBank.getQuestions(knownAttributes));
            Assertions.assertFalse(questionQueue.isEmpty());
            /*
            This goes through the entire chatbot with the first valid answer and checks if we get a proper recommendation.
             */
            while (!questionQueue.isEmpty()) {
                PlanQuestion currentQuestion = questionQueue.peek();
                questionQueue.remove();
                Field field = PlanQuestion.class.getDeclaredField("answersAttributesValue");
                field.setAccessible(true);
                Map<String, String> answerAttributeValue = (Map<String, String>) field.get(currentQuestion);
                String answer = answerAttributeValue.keySet().iterator().next();
                if (answer.equals("DOUBLE")) {
                    answer = "10.00";
                }
                PlanQuestion.Reply currentQuestionReply = currentQuestion.getReply(answer, knownAttributes);
                Assertions.assertFalse(currentQuestionReply.getAttributes().isEmpty());
                Assertions.assertEquals(currentQuestionReply.getText(), "Ok noted!");
                knownAttributes = currentQuestionReply.getAttributes();
                questionQueue.addAll(planQuestionBank.getQuestions(knownAttributes));
            }
            PlanQuestionBank.PlanRecommendation recommendation = planQuestionBank
                    .makeRecommendation(knownAttributes);
            Assertions.assertNotEquals(recommendation.recommendation,"I can't make any recommendations for you"
                    + " :(. Something probably went wrong" );
            Assertions.assertFalse(recommendation.getPlanBudget().isEmpty());
            Assertions.assertNotNull(recommendation.getRecommendationExpenseList());
        } catch (DukeException | NoSuchFieldException | IllegalAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }


}
