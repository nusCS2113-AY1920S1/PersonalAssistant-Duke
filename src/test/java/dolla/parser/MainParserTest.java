package dolla.parser;

import dolla.ModeStringList;
import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainParserTest implements ModeStringList {

    private Command command;

    @Test
    public void parseInDollaModeTest() {
        String emptyStr = " ";
        String errorCommand = "ErrorCommand";
        command = MainParser.handleInput(MODE_DOLLA, emptyStr);
        assertEquals(errorCommand, command.getCommandInfo());

        String bye = "bye";
        String expectedStr1 = "Exit";
        command = MainParser.handleInput(MODE_DOLLA, bye);
        assertEquals(expectedStr1, command.getCommandInfo());

        String switchEntry = MODE_ENTRY;
        command = MainParser.handleInput(MODE_DOLLA, switchEntry);
        assertEquals(switchEntry, command.getCommandInfo());

        String switchLimit = MODE_LIMIT;
        command = MainParser.handleInput(MODE_DOLLA, switchLimit);
        assertEquals(switchLimit, command.getCommandInfo());

        String switchDebt = MODE_DEBT;
        command = MainParser.handleInput(MODE_DOLLA, switchDebt);
        assertEquals(switchDebt, command.getCommandInfo());

        String switchShortcut = MODE_SHORTCUT;
        command = MainParser.handleInput(MODE_DOLLA, switchShortcut);
        assertEquals(switchShortcut, command.getCommandInfo());
    }
}
