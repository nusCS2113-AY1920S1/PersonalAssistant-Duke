package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;

import java.util.ArrayList;

public class ClearCommand extends Command {

    private static final String CLEAR_MESSAGE = "All tasks cleared from schedule.\n";

    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        ArrayList<Task> emptyTasks = new ArrayList<>();
        tasks.updateListOfTasks(emptyTasks);
        tasks.updateGui(null);
        storage.saveFile(tasks.getTasks());
        UiMessageHandler.outputMessage(CLEAR_MESSAGE);
    }
}
