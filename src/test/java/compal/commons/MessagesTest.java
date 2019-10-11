package compal.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessagesTest {
    private Messages messages;

    @BeforeEach
    public void setUp() {
        messages = new Messages();
    }

    @Test
    public void messageTest() {
        assertEquals("CommandError: Unknown command input detected!",messages.MESSAGE_INVALID_COMMAND);
    }
}
