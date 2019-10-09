package seedu.duke.task.command;

import seedu.duke.Duke;
import seedu.duke.common.command.Command;
import seedu.duke.task.entity.Deadline;
import seedu.duke.task.entity.Event;
import seedu.duke.task.entity.Task;
import seedu.duke.task.TaskList;
import seedu.duke.task.entity.ToDo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * AddCommand is a specific kind of command used to add task to the task list.
 */
public class TaskAddCommand extends Command {
    private TaskList taskList;
    private Task.TaskType taskType;
    private String name;
    private LocalDateTime time;
    private String doAfter;
    private ArrayList<String> tags;

    /**
     * Instantiation of add command with all the necessary variables. it needs to execute.
     *
     * @param taskList the task list where the task is added to.
     * @param taskType the type of task that is to be added.
     * @param name     he name of the task, which is needed to instantiate the task.
     * @param time     the time of the task, which is needed to instantiate the task. ToDo tasks does not have
     *                 time attribute, so any Date can be passed in and will be ignored.
     */
    public TaskAddCommand(TaskList taskList, Task.TaskType taskType, String name, LocalDateTime time,
                          String doAfter, ArrayList<String> tags) {
        this.taskList = taskList;
        this.taskType = taskType;
        this.name = name;
        this.time = time;
        this.doAfter = doAfter;
        this.tags = tags;
    }

    /**
     * Executes the add command by instantiating the task first and then add the task to task list.
     *
     * @return a flag whether the task is successfully added. Returns false if the taskType is not
     *         recognised.
     */
    @Override
    public boolean execute() {
        Task task;
        switch (taskType) {
        case ToDo:
            task = new ToDo(name, doAfter, tags);
            break;
        case Deadline:
            task = new Deadline(name, time, doAfter, tags);
            break;
        case Event:
            task = new Event(name, time, doAfter, tags);
            break;
        default:
            return false;
        }
        TaskList clashTasks = taskList.findClash(task);
        String clashMsg = "";
        if (clashTasks.size() > 0) {
            clashMsg = "\n\nWarning: New task added clashes with other task(s) in the list.\n";
            clashMsg += clashTasks.toString();
        }
        taskList.add(task);
        if (!silent) {
            String msg = "Got it. I've added this task: \n";
            msg += "  " + task.toString() + "\n";
            msg += "Now you have " + taskList.size() + " task(s) in the list. ";
            msg += clashMsg;
            responseMsg = msg;
            Duke.getUI().showResponse(msg);
        }
        return true;
    }
}
