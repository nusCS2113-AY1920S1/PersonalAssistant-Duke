package duke.logic.conversations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteConversationTest {

    @Test
    void execute() {
        DeleteConversation deleteConversation = new DeleteConversation();
        deleteConversation.execute("one");
        assertFalse(deleteConversation.isFinished());
    }

    @Test
    void buildResult() {
        DeleteConversation deleteConversation = new DeleteConversation();
        deleteConversation.execute("1");
        assertTrue(deleteConversation.isFinished());
        deleteConversation.buildResult();
        assertEquals("delete 1", deleteConversation.getResult());
    }
}
