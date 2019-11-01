package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.model.lists.RouteList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteEditConversationTest {

    @Test
    void testForName() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeEdit");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-existant field
        conversationManager.converse("not_field");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_ROUTE_FIELD, conversationManager.getPrompt());

        conversationManager.converse("name");
        assertFalse(conversationManager.isFinished());

        String expected = "routeEdit 1 name test_name";
        conversationManager.converse("test_name");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }

    @Test
    void testForDescription() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeEdit");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-existant field
        conversationManager.converse("not_field");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_ROUTE_FIELD, conversationManager.getPrompt());

        conversationManager.converse("description");
        assertFalse(conversationManager.isFinished());

        String expected = "routeEdit 1 description test_description";
        conversationManager.converse("test_description");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
