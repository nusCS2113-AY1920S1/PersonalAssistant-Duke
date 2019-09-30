package wrapper;


import parser.CommandParser;
import task.Tasks;

public class CommandPair {
    private CommandParser.Commands RootCommand;
    private CommandParser.Commands SubCommand;

    public CommandPair(CommandParser.Commands rt, CommandParser.Commands st) {
        this.RootCommand = rt;
        this.SubCommand = st;
    }

    public CommandParser.Commands getRoot() {
        return RootCommand;
    }

    public CommandParser.Commands getSub() {
        return SubCommand;
    }
}
