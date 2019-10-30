package dolla.parser;

import dolla.command.Command;

public abstract class CommandParser {

    public CommandParser() {

    }

    public abstract Command parse();

}
