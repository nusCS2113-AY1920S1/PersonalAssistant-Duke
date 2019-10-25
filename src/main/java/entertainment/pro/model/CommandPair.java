package entertainment.pro.model;

import entertainment.pro.commons.enums.COMMANDKEYS;

/**
 * Template command pair class containing the root command and subroot command
 */
public class CommandPair {
    private COMMANDKEYS RootCommand;
    private COMMANDKEYS SubRootCommand;
    private boolean validCommand;

    public CommandPair(COMMANDKEYS rootCommand, COMMANDKEYS subRootCommand) {
        RootCommand = rootCommand;
        SubRootCommand = subRootCommand;
        validCommand = true;
    }

    public CommandPair(COMMANDKEYS rootCommand, COMMANDKEYS subRootCommand , boolean isValid) {
        RootCommand = rootCommand;
        SubRootCommand = subRootCommand;
        validCommand = isValid;
    }

    /**
     * getter for the root command
     * @return Rootcommand
     */
    public COMMANDKEYS getRootCommand() {
        return RootCommand;
    }

    /**
     * Getter for subroot command
     * @return Subroot command
     **/
    public COMMANDKEYS getSubRootCommand() {
        return SubRootCommand;
    }

    /**
     * function to check if the command was valid.
     * USed to determine if the command should be executed.
     * @return
     */
    public boolean isValidCommand() {
        return validCommand;
    }

    public void setValidCommand(boolean validCommand) {
        this.validCommand = validCommand;
    }
}
