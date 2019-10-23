package duke.logic.conversations;

import duke.commons.MessagesPrompt;
import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversationManagerTest {

    @Test
    void converse() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("todo");
        assertFalse(conversationManager.isFinished());
        conversationManager.converse("Travel the Seven seas");
        assertTrue(conversationManager.isFinished());
        assertEquals(conversationManager.getResult(), "todo Travel the Seven seas");
    }

    @Test
    void getResult() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("todo");
        assertFalse(conversationManager.isFinished());
        conversationManager.converse("Travel the Seven seas");
        assertTrue(conversationManager.isFinished());
        assertEquals(conversationManager.getResult(), "todo Travel the Seven seas");
    }

    @Test
    void getPrompt() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("delete");
        assertEquals(conversationManager.getPrompt(), MessagesPrompt.DELETE_PROMPT_STARTER);
        conversationManager.converse("ooh aah ooh aah");
        assertEquals(conversationManager.getPrompt(), MessagesPrompt.PROMPT_NOT_INT);
    }

    @Test
    void clearContext() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("done");
        assertFalse(conversationManager.isFinished());
        conversationManager.clearContext();
        assertTrue(conversationManager.isFinished());
    }
}
