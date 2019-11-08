package dolla.command;

import dolla.model.DollaData;

/**
 * A command that is generated if a proper command could not be produced (as a result of invalid inputs).
 */
public class ErrorCommand extends Command {

    @Override
    public void execute(DollaData dollaData) {
        return;
    }

    @Override
    public String getCommandInfo() {
        return "ErrorCommand";
    }
}
