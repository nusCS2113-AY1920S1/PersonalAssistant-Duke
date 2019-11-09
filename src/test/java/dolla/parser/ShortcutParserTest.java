package dolla.parser;

import dolla.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortcutParserTest {
    private String inputLine;
    private String expectedCommand;

    @Test
    public void parseValidShortcutListTest() {
        inputLine = "shortcuts";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidShortcutListTest() {
        inputLine = "invalid";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidEntryListTest() {
        inputLine = "entries";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "entry";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidEntryListTest() {
        inputLine = "anotherInvalid";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidRemoveTest() {
        inputLine = "remove 1";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidRemoveTest() {
        inputLine = "remove 0";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidCreateShortcutTest() {
        inputLine = "cs 1";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "create shortcut 1 in mode shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidCreateShortcutTest() {
        inputLine = "cs 0";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidExecuteShortcutTest() {
        inputLine = "es 1";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "execute shortcut 1 in mode shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidExecuteShortcutTest() {
        inputLine = "es -1";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidRedoTest() {
        inputLine = "redo";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "redo in shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidUndoTest() {
        inputLine = "undo";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "undo in shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidSortTest1() {
        inputLine = "sort amount";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "sort amount in shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidSortTest2() {
        inputLine = "sort description";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "sort description in shortcut";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidSortTest1() {
        inputLine = "sort date";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidSortTest2() {
        inputLine = "sort name";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseValidSearchTest() {
        inputLine = "search description tata";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "description tata";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidSearchTest() {
        inputLine = "search yuyu";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidCommandTest1() {
        inputLine = "cs";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidCommandTest2() {
        inputLine = "sort";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }

    @Test
    public void parseInvalidCommandTest3() {
        inputLine = "search";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedCommand = "ErrorCommand";
        assertEquals(expectedCommand, c.getCommandInfo());
    }





}
