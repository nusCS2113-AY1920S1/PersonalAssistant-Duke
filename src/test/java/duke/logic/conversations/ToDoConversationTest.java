package duke.logic.conversations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToDoConversationTest {

    @Test
    void execute() {
        ToDoConversation conversation = new ToDoConversation();
        conversation.execute("Go Orchard road");
        assertTrue(conversation.isFinished());
    }

    @Test
    void buildResult() {
        ToDoConversation conversation = new ToDoConversation();
        conversation.execute("Go NTU");
        conversation.buildResult();
        assertEquals("todo Go NTU", conversation.getResult());
    }
}
