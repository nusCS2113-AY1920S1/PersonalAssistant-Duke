package dolla.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelpCommandTest {

    @Test
    public void helpCommandTest() {
        String expected = "help";
        Command commandTest = new HelpCommand();
        assertEquals(expected, commandTest.getCommandInfo());
    }
}
