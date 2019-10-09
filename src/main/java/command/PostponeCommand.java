package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostponeCommand extends Command {

    private int indexOfTask;
    private final LocalDateTime nullDate = LocalDateTime.of(1, 1, 1, 1, 1, 1, 1);
    private LocalDateTime atDate = nullDate;
    private LocalDateTime toDate = nullDate;
    private LocalDateTime fromDate = nullDate;

    /**
     * creates a postpone task command.
     *
     * @param indexOfTask index of task in loaded tasks list
     * @param atDate      current start time of task
     * @param fromDate    new start time of task
     * @param toDate      new end time of task
     */
    public PostponeCommand(int indexOfTask, LocalDateTime atDate, LocalDateTime fromDate, LocalDateTime toDate) {
        this.indexOfTask = indexOfTask;
        this.atDate = atDate;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1)) {
            throw new DukeException(DukeException.taskDoesNotExist());
        }

        ArrayList<Task> taskList = tasks.getTasks();
        Task taskToBeChanged = taskList.get(indexOfTask);
        String description = taskToBeChanged.description;
        String oldTime = taskToBeChanged.toString();

        if (taskToBeChanged.toString().contains("[E]")) {
            Task checkTask = new Event(description, toDate, fromDate);
            if (tasks.isClash(checkTask)) {
                throw new DukeException(DukeException.taskClash());
            } else {
                tasks.updateDate(taskToBeChanged, "event", atDate, toDate);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput("Got it! I've postponed this event:" + "\n  " + oldTime
                    + "\nNow the tasks details are:\n  " + taskToBeChanged.toString());
            }
        } else if (taskToBeChanged.toString().contains("[D]")) {
            Task checkTask = new Deadline(description, atDate);
            if (tasks.isClash(checkTask)) {
                throw new DukeException(DukeException.taskClash());
            } else {
                tasks.updateDate(taskToBeChanged, "deadline", atDate, toDate);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput("Got it! I've postponed this deadline:" + "\n  " + oldTime
                    + "\nNow the tasks details are:\n  " + taskToBeChanged.toString());
            }
        } else {
            Ui.printOutput("Todo tasks has no schedule");
        }

    }
}
