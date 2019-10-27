package duke.logic.conversations;

import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteAddConversationTest {

    @Test
    void converse() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeAdd");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("test_route");
        assertFalse(conversationManager.isFinished());

        String expected = "routeAdd test_route desc test_description";
        conversationManager.converse("test_description");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
