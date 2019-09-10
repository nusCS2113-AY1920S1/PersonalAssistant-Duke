import java.io.IOException;

/**
 * Command to exit Duke programme
 */
public class ByeCommand extends Command {

    /**
     * If "bye" is entered,
     * @param tasks task list
     * @param ui user interface
     * @param storage handles read write of text file
     * @throws IOException
     * @throws DukeException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException {
        super.execute(tasks, ui, storage);
        ui.showBye();
        this.isExit = true;
    }
}
