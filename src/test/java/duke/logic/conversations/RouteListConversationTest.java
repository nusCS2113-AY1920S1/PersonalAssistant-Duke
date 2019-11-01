package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.model.lists.RouteList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteListConversationTest {

    @Test
    void converse() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeList");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        String expected = "routeList 1";
        conversationManager.converse("1");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
