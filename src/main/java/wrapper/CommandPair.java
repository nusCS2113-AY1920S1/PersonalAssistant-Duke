package wrapper;

import commands.COMMANDKEYS;

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

    public COMMANDKEYS getRootCommand() {
        return RootCommand;
    }

    public COMMANDKEYS getSubRootCommand() {
        return SubRootCommand;
    }

    public boolean isValidCommand() {
        return validCommand;
    }

    public void setValidCommand(boolean validCommand) {
        this.validCommand = validCommand;
    }
}
