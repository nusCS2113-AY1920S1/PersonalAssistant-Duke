package entertainment.pro.model;

import entertainment.pro.commons.enums.CommandKeys;

/**
 * Template command pair class containing the root command and subroot command.
 */
public class CommandPair {
    private CommandKeys rootCommand;
    private CommandKeys subRootCommand;
    private boolean validCommand;

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public CommandPair(CommandKeys rootCommand, CommandKeys subRootCommand) {
        this.rootCommand = rootCommand;
        this.subRootCommand = subRootCommand;
        validCommand = true;
    }

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public CommandPair(CommandKeys rootCommand, CommandKeys subRootCommand, boolean isValid) {
        this.rootCommand = rootCommand;
        this.subRootCommand = subRootCommand;
        validCommand = isValid;
    }

    /**
     * getter for the root command.
     * @return Rootcommand
     */
    public CommandKeys getRootCommand() {
        return rootCommand;
    }

    /**
     * getter for the root command in String.
     * @return Rootcommand
     */
    public String getRootCommandStr() {
        return rootCommand.toString().toLowerCase();
    }

    /**
     * getter for the subroot command in String.
     * @return subRootcommand
     */
    public String getSubRootCommandStr() {
        return subRootCommand.toString().toLowerCase();
    }

    /**
     * Getter for subroot command.
     * @return Subroot command
     **/
    public CommandKeys getSubRootCommand() {
        return subRootCommand;
    }

    /**
     * function to check if the command was valid.
     * Used to determine if the command should be executed.
     * @return
     */
    public boolean isValidCommand() {
        return validCommand;
    }

    public void setValidCommand(boolean validCommand) {
        this.validCommand = validCommand;
    }
}
