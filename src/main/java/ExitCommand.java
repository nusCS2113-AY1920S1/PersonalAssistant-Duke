public class ExitCommand extends Command {
    public ExitCommand() {
        super();
    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.exitInformation();
    }
}