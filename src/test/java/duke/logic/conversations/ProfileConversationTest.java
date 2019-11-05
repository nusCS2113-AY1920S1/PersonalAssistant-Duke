package duke.logic.conversations;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileConversationTest {
    @Test
    void converse() throws DukeException {
        ConversationManager conversationManager = new ConversationManager();
        conversationManager.converse("profile");
        assertFalse(conversationManager.isFinished());

        conversationManager.converse("name");
        assertFalse(conversationManager.isFinished());

        //negative test, put in non-integer
        conversationManager.converse("not_an_date");
        assertFalse(conversationManager.isFinished());
        assertEquals(Messages.PROMPT_NOT_DATE, conversationManager.getPrompt());

        conversationManager.converse("01/01/01");

        String expected = "profile name 01/01/01";
        assertTrue(conversationManager.isFinished());
        assertEquals(expected, conversationManager.getResult());
    }
}
