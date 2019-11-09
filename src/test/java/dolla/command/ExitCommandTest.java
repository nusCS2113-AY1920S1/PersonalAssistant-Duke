package dolla.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExitCommandTest {

    @Test
    public void exitCommandTest() {
        String expectedCommandInfo = "Exit";
        Command c = new ExitCommand();
        assertEquals(expectedCommandInfo, c.getCommandInfo());
    }
}
