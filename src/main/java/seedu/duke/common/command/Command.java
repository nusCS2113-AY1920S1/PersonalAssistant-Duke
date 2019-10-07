package seedu.duke.common.command;

/**
 * This is an abstract command that all kinds of command inherit from. It has execute() function to be
 * overridden and can be set silent for UI output.
 */
public abstract class Command {
    /**
     * A flag whether UI output is turned off. Default to be false, so UI output is default to be turned on.
     */
    protected boolean silent = false;
    protected String responseMsg = "";

    /**
     * Executes the command. This method is to be overridden to specific functionality by different types of
     * command themselves.
     *
     * @return a flag whether the command is successfully executed.
     */
    public abstract boolean execute();

    /**
     * This function turns off the UI output of the command.
     */
    public void setSilent() {
        this.silent = true;
    }

    public String getResponseMsg() {
        return responseMsg;
    }
}
