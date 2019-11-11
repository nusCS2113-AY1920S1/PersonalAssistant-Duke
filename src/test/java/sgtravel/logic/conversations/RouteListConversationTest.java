package sgtravel.logic.conversations;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.SingaporeTravelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteListConversationTest {

    @Test
    void converse() throws SingaporeTravelException {
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
