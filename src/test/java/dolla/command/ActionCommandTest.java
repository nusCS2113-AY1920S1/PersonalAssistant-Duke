package dolla.command;

import dolla.ModeStringList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author yetong1895
public class ActionCommandTest {
    private final String REDO = "redo";
    private final String UNDO = "undo";
    private String expected;

    @Test
    public void actionCommandTest1() {
        Command commandTest = new ActionCommand(ModeStringList.MODE_ENTRY, REDO);
        expected = "redo in entry";
        assertEquals(expected,commandTest.getCommandInfo());
    }

    @Test
    public void actionCommandTest2() {
        Command commandTest = new ActionCommand(ModeStringList.MODE_DEBT, UNDO);
        expected = "undo in debt";
        assertEquals(expected,commandTest.getCommandInfo());
    }

    @Test
    public void actionCommandTest3() {
        Command commandTest = new ActionCommand(ModeStringList.MODE_LIMIT, REDO);
        expected = "redo in limit";
        assertEquals(expected,commandTest.getCommandInfo());
    }

    @Test
    public void actionCommandTest4() {
        Command commandTest = new ActionCommand(ModeStringList.MODE_SHORTCUT, UNDO);
        expected = "undo in shortcut";
        assertEquals(expected,commandTest.getCommandInfo());
    }
}
