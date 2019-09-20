package wallet.logic.command;

public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "bye";

    @Override
    public boolean execute() {
        return true;
    }
}
