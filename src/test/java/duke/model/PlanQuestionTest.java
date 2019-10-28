package duke.model;

import duke.exception.DukeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlanQuestionTest {

    private static final String[] BOOL_ANSWERS = {"YES", "NO"};
    private static final String[] BOOL_ATTRIBUTE_VALUES = {"TRUE", "FALSE"};
    private static final Integer[] NEIGHBOUR_ARR = {1, 2};
    private static final Set<Integer> NEIGHBOUR_SET = new HashSet<>(Arrays.asList(NEIGHBOUR_ARR));
    private static final Map<String, String> KNOWN_ATTRIBUTES = new HashMap<>();
    private static final Map<String, String> UPDATED_ATTRIBUTES = new HashMap<>();

    @Test
    public void testConstruction() {
        try {
            PlanQuestion question = new PlanQuestion("Test Question",
                    BOOL_ANSWERS,
                    BOOL_ATTRIBUTE_VALUES,
                    "TEST");
            Assertions.assertEquals(question.getQuestion(), "Test Question");
            Assertions.assertEquals(question.getAttribute(), "TEST");
        } catch (DukeException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testPositive() {
        try {
            PlanQuestion question = new PlanQuestion("Test Question",
                    BOOL_ANSWERS,
                    BOOL_ATTRIBUTE_VALUES,
                    "TEST");
            PlanQuestion.Reply reply = question.getReply("yes", KNOWN_ATTRIBUTES);
            Assertions.assertEquals(reply.getText(), "Ok noted!");
            UPDATED_ATTRIBUTES.put("TEST", "TRUE");
            question.addNeighbouring("TRUE", 1);
            question.addNeighbouring("TRUE", 2);
            Assertions.assertEquals(question.getNeighbouringQuestions("TRUE"), NEIGHBOUR_SET);
            Assertions.assertEquals(reply.getAttributes(), UPDATED_ATTRIBUTES);
        } catch (DukeException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testNegative() {
        try {
            PlanQuestion question = new PlanQuestion("Test Question",
                    BOOL_ANSWERS,
                    BOOL_ATTRIBUTE_VALUES,
                    "TEST");
            Assertions.assertThrows(DukeException.class, ()-> {
               question.getReply("some random input", KNOWN_ATTRIBUTES);
            });
            Assertions.assertThrows(DukeException.class, ()-> {
                question.addNeighbouring("SOME_RANDOM_ATTRIBUTE", 1);
            });
            Assertions.assertThrows(DukeException.class, ()-> {
                question.getNeighbouringQuestions("SOME_RANDOM_ATTRIBUTE");
            });
        } catch (DukeException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
