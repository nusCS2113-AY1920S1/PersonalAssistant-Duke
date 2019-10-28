package duke.command;


import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.util.Optional;

/**
 * duke.command.DoneCommand that deals with marking Tasks in the duke.tasklist.TaskList as done
 */
public class DoneCommand extends Command {
    private int index;
    Optional<String> filter;

    public DoneCommand(Optional<String> filter, String index) {
        this.filter = filter;
        this.index = Integer.parseInt(index) ;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        Task t = tasks.get(filter, index);
        t.markAsDone();
        ui.showLine("Congratulations on completing the following task:");
        ui.showLine(t.getDescription());
		storage.save(tasks);
    }

    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
        Task t = tasks.get(filter, index);
        undoStack.addAction(new SetCommand(filter, index, new Task(t)));
        System.out.println(t);
    }
}