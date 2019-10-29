package logic.command;

import core.Duke;
import model.Model;
import model.Task;
import model.TasksManager;
import utils.DukeException;

public class DoneCommand extends Command{
    int[] indexes;

    public DoneCommand(int[] indexes) {
        this.indexes = indexes;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        TasksManager tasksManager = model.getTasksManager();
        int taskListLength = model.getTaskList().size();
        for (int i = 0; i < indexes.length; i++) {
            if (indexes[i] < 0 || indexes[i] >= taskListLength) {
                throw new DukeException("Index " + (i + 1) + " out of range.\n Please try again.");
            }
        }
        String output = "";
        for (int i = 0; i < indexes.length; i++) {
            Task task = tasksManager.getTaskById(indexes[i]);
            if (task.isDone()) {
                output += "Task " + task.getName() + " has already been done.\n";
            } else {
                task.markAsDone();
                output += "Noted, marked task " + task.getName() + " as done.\n";
            }
        }
        model.save();
        return new CommandOutput(output);
    }

}
