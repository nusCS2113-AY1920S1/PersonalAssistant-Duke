package seedu.duke.command;

/**
 * Invalid Command is a specific kind of command indicating the input is not recognised as any known command.
 */
public class InvalidCommand extends Command {

    /**
     * Simply return false. The UI output is currently disabled.
     *
     * @return false.
     */
    @Override
    public boolean execute() {
        //if (!silent) {
        //    Duke.getUI().showError("Invalid Command Received");
        //}
        return false;
    }
}
