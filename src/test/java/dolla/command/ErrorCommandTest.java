package dolla.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorCommandTest {

    @Test
    public void errorCommandTest1() {
        Command commandTest = new ErrorCommand();
        String expected = "ErrorCommand";
        assertEquals(expected, commandTest.getCommandInfo());
    }
}