package wrapper;

import commands.COMMANDKEYS;

public class CommandPair {
    private COMMANDKEYS RootCommand;
    private COMMANDKEYS SubRootCommand;

    public CommandPair(COMMANDKEYS rootCommand, COMMANDKEYS subRootCommand) {
        RootCommand = rootCommand;
        SubRootCommand = subRootCommand;
    }

    public COMMANDKEYS getRootCommand() {
        return RootCommand;
    }

    public COMMANDKEYS getSubRootCommand() {
        return SubRootCommand;
    }
}
