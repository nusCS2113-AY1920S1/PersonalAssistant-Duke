package duke.logic.conversations;

import duke.commons.Messages;
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
        assertEquals(conversationManager.getPrompt(), Messages.PROMPT_DELETE_STARTER);
        conversationManager.converse("ooh aah ooh aah");
        assertEquals(conversationManager.getPrompt(), Messages.PROMPT_NOT_INT);
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
