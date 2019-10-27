package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteNodeDeleteConversationTest {

    @Test
    void converse() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeDelete");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer for first index
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer for first index
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        String expected = "routeNodeDelete 1 1";
        conversationManager.converse("1");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
