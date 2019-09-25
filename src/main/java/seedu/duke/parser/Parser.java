package seedu.duke.parser;

import seedu.duke.logic.CommandLineException;

/**
 * Abstract class for parsing.
 */
public abstract class Parser {
    /**
     * Abstract method that parses string description to another object.
     */
    public abstract Object parse(String description) throws CommandLineException;
}
