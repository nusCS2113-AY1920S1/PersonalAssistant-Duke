package sgtravel.logic.conversations;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteNodeAddConversationTest {

    @Test
    void testForBus() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeAdd");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer for first index
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer for second index
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-existant constraint
        conversationManager.converse("not_constraint");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.ERROR_CONSTRAINT_UNKNOWN, conversationManager.getPrompt());

        conversationManager.converse("bus");
        assertFalse(conversationManager.isFinished());

        String expected = "routeNodeAdd 1 1 at 17009 by bus";
        conversationManager.converse("17009");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }

    @Test
    void testForTrain() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("routeNodeAdd");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer for first index
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer for second index
        conversationManager.converse("not_an_int");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_INT, conversationManager.getPrompt());

        conversationManager.converse("1");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-existant constraint
        conversationManager.converse("not_constraint");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.ERROR_CONSTRAINT_UNKNOWN, conversationManager.getPrompt());

        conversationManager.converse("mrt");
        assertFalse(conversationManager.isFinished());

        String expected = "routeNodeAdd 1 1 at 17009 by mrt";
        conversationManager.converse("17009");
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
