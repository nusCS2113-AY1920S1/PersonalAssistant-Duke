package seedu.hustler.command;

import java.io.IOException;

/**
 * Template for duke commands.
 */
public abstract class Command {
    /**
     * Executes the command.
     */
    public abstract void execute() throws IOException;
}
