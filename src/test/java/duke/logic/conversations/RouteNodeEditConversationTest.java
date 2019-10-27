package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteNodeEditConversationTest {

    @Test
    void testForAddress() throws DukeException {
        String expected = "routeNodeEdit 1 1 address test_address";

        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeEdit");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
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
        assertEquals(Messages.PROMPT_NOT_ROUTENODE_FIELD, conversationManager.getPrompt());

        conversationManager.converse("address");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("test_address");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }

    @Test
    void testForDescription() throws DukeException {
        String expected = "routeNodeEdit 1 1 description test_description";

        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeEdit");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
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
        assertEquals(Messages.PROMPT_NOT_ROUTENODE_FIELD, conversationManager.getPrompt());

        conversationManager.converse("description");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("test_description");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }

    @Test
    void testForType() throws DukeException {
        String expected = "routeNodeEdit 1 1 type test_type";

        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeEdit");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
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
        assertEquals(Messages.PROMPT_NOT_ROUTENODE_FIELD, conversationManager.getPrompt());

        conversationManager.converse("type");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("test_type");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }

    @Test
    void testForLatitude() throws DukeException {
        String expected = "routeNodeEdit 1 1 latitude test_latitude";

        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeEdit");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
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
        assertEquals(Messages.PROMPT_NOT_ROUTENODE_FIELD, conversationManager.getPrompt());

        conversationManager.converse("latitude");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("test_latitude");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }

    @Test
    void testForLongitude() throws DukeException {
        String expected = "routeNodeEdit 1 1 longitude test_longitude";

        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeEdit");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
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
        assertEquals(Messages.PROMPT_NOT_ROUTENODE_FIELD, conversationManager.getPrompt());

        conversationManager.converse("longitude");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("test_longitude");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
