package dolla.parser;

import dolla.ModeStringList;
import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainParserTest implements ModeStringList {

    @Test
    public void parseInDollaModeTest() {
        String mode = MODE_DOLLA;
        Command command;

        String emptyStr = "";
        String errorCommand = "ErrorCommand";
        command = MainParser.handleInput(mode, emptyStr);
        assertEquals(errorCommand, command.getCommandInfo());

        String space = " ";
        command = MainParser.handleInput(mode, space);
        assertEquals(errorCommand, command.getCommandInfo());

        String bye = "bye";
        String expectedStr1 = "Exit";
        command = MainParser.handleInput(mode, bye);
        assertEquals(expectedStr1, command.getCommandInfo());

        String switchEntry = MODE_ENTRY;
        command = MainParser.handleInput(mode, switchEntry);
        assertEquals(switchEntry, command.getCommandInfo());

        String switchLimit = MODE_LIMIT;
        command = MainParser.handleInput(mode, switchLimit);
        assertEquals(switchLimit, command.getCommandInfo());

        String switchDebt = MODE_DEBT;
        command = MainParser.handleInput(mode, switchDebt);
        assertEquals(switchDebt, command.getCommandInfo());

        String switchShortcut = MODE_SHORTCUT;
        command = MainParser.handleInput(mode, switchShortcut);
        assertEquals(switchShortcut, command.getCommandInfo());
    }

    @Test
    public void parseInEntryModeTest() {
        String mode = MODE_ENTRY;
        Command command;

        String emptyStr = " ";
        String expectedStr = "ErrorCommand";
        command = MainParser.handleInput(mode, emptyStr);
        assertEquals(expectedStr, command.getCommandInfo());

        String bye = "bye";
        String expectedStr1 = "Exit";
        command = MainParser.handleInput(mode, bye);
        assertEquals(expectedStr1, command.getCommandInfo());

        String switchEntry = MODE_ENTRY;
        command = MainParser.handleInput(mode, switchEntry);
        assertEquals(switchEntry, command.getCommandInfo());

        String switchLimit = MODE_LIMIT;
        command = MainParser.handleInput(mode, switchLimit);
        assertEquals(switchLimit, command.getCommandInfo());

        String switchDebt = MODE_DEBT;
        command = MainParser.handleInput(mode, switchDebt);
        assertEquals(switchDebt, command.getCommandInfo());

        String switchShortcut = MODE_SHORTCUT;
        command = MainParser.handleInput(mode, switchShortcut);
        assertEquals(switchShortcut, command.getCommandInfo());
    }
}
