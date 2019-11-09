package dolla.parser;

import dolla.ModeStringList;
import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainParserTest implements ModeStringList {

    private static final String errorCommand = "ErrorCommand";

    @Test
    public void parseInDollaModeTest() {
        String mode = MODE_DOLLA;
        Command command;

        String emptyStr = "";
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

        String viewEntries = "entries";
        command = MainParser.handleInput(mode, viewEntries);
        assertEquals(MODE_ENTRY, command.getCommandInfo());

        String addIncomeStr = "add income 40 tuition /on 12/11/2019";
        String expectedStr = "AddEntryCommand{ income, 40.0, tuition, 12/11/2019}";
        command = MainParser.handleInput(mode, addIncomeStr);
        assertEquals(expectedStr, command.getCommandInfo());

        String addExpenseStr = "add expense 4 coffee /on 12/12/2019";
        String expectedStr1 = "AddEntryCommand{ expense, 4.0, coffee, 12/12/2019}";
        command = MainParser.handleInput(mode, addExpenseStr);
        assertEquals(expectedStr1, command.getCommandInfo());

        String invalidStr = "add income";
        command = MainParser.handleInput(mode, invalidStr);
        assertEquals(errorCommand, command.getCommandInfo());
    }


    @Test
    public void parseInLimitModeTest() {
        String mode = MODE_LIMIT;
        Command command;

        String viewLimits = "limits";
        command = MainParser.handleInput(mode, viewLimits);
        assertEquals(MODE_LIMIT, command.getCommandInfo());

        String addValidBudgetStr = "set budget 50 daily";
        String expectedStr = "budget 50.0 daily";
        command = MainParser.handleInput(mode, addValidBudgetStr);
        assertEquals(expectedStr, command.getCommandInfo());

        String invalidAddBudgetStr = "set budget daily";
        command = MainParser.handleInput(mode, invalidAddBudgetStr);
        assertEquals(errorCommand, command.getCommandInfo());

        String switchDolla = MODE_DOLLA;
        command = MainParser.handleInput(mode, switchDolla);
        assertEquals(switchDolla, command.getCommandInfo());

        String validShowStr = "remaining weekly saving";
        String expectedStr2 = "weekly saving";
        command = MainParser.handleInput(mode, validShowStr);
        assertEquals(expectedStr2, command.getCommandInfo());

        String invalidShowStr = "remaining budget";
        command = MainParser.handleInput(mode, invalidShowStr);
        assertEquals(errorCommand, command.getCommandInfo());
    }
}
