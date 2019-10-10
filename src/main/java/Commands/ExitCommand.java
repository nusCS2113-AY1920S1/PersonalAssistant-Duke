package Commands;

public class ExitCommand extends Command {
    @Override
    public void execute() {
        super.isExit = true;
    }
}
