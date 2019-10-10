package wrapper;

import Commands.COMMAND_KEYS;

public class CommandPair {
    private COMMAND_KEYS RootCommand;
    private COMMAND_KEYS SubRootCommand;

    public CommandPair(COMMAND_KEYS rootCommand, COMMAND_KEYS subRootCommand) {
        RootCommand = rootCommand;
        SubRootCommand = subRootCommand;
    }

    public COMMAND_KEYS getRootCommand() {
        return RootCommand;
    }

    public COMMAND_KEYS getSubRootCommand() {
        return SubRootCommand;
    }
}
