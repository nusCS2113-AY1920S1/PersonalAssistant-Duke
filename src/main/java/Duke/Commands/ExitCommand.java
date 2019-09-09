package Duke.Commands;

import Duke.Storage;
import Duke.TaskList;
import Duke.Ui;

public class ExitCommand extends Command {
    public ExitCommand() {
        type = CmdType.EXIT;
    }

    /**
     * Executes exiting the program
     * @param tasks TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("\nBye. Hope to see you again soon!\n");
    }
}
