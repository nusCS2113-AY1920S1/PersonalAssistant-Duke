package dolla.command;

import dolla.parser.ParserStringList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddLimitCommandTest implements ParserStringList {

    @Test
    public void errorCommandTest1() {
        Command commandTest = new AddLimitCommand(LIMIT_TYPE_S, 50, LIMIT_DURATION_D);
        String expected = "saving 50.0 daily";
        assertEquals(expected, commandTest.getCommandInfo());
    }
}
