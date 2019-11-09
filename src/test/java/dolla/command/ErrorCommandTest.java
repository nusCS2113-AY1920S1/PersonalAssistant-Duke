package dolla.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorCommandTest {

    @Test
    public void errorCommandTest() {
        String expected = "ErrorCommand";
        Command commandTest = new ErrorCommand();
        assertEquals(expected, commandTest.getCommandInfo());
    }
}