package dolla.command;

import dolla.parser.ParserStringList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddLimitCommandTest implements ParserStringList {

    @Test
    public void addLimitCommandTest1() {
        Command commandTest = new AddLimitCommand(LIMIT_TYPE_S, 50, LIMIT_DURATION_D);
        String expected = "saving 50.0 daily";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void addLimitCommandTest2() {
        Command commandTest = new AddLimitCommand(LIMIT_TYPE_B, 50, LIMIT_DURATION_D);
        String expected = "budget 50.0 daily";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void addLimitCommandTest3() {
        Command commandTest = new AddLimitCommand(LIMIT_TYPE_S, 50, LIMIT_DURATION_M);
        String expected = "saving 50.0 monthly";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void addLimitCommandTest4() {
        Command commandTest = new AddLimitCommand(LIMIT_TYPE_S, 500, LIMIT_DURATION_W);
        String expected = "saving 500.0 weekly";
        assertEquals(expected, commandTest.getCommandInfo());
    }
}
