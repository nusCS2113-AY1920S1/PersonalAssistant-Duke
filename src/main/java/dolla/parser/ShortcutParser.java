package dolla.parser;

import dolla.command.Command;

public class ShortcutParser extends Parser {

    /**
     * Creates an instance of a parser.
     *
     * @param inputLine The entire string containing the user's input.
     */
    public ShortcutParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command parseInput() {
        return null;
    }
}
