package dolla.parser;

import dolla.command.Command;

public class ShortcutParserTest {
    private String inputLine;
    private String expectedValidCommand;

    public void parseValidListTest() {
        inputLine = "shortcuts";
        ShortcutParser shortcutParser = new ShortcutParser(inputLine);
        Command c = shortcutParser.parseInput();
        expectedValidCommand = "shortcut";

    }
}
