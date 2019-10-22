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
    private Task.TaskType taskType;
    private String name;
    private LocalDateTime time;
    private String doAfter;
    private ArrayList<String> tags;
    private String priority;

    /**
     * Instantiation of add command with all the necessary variables. it needs to execute.
     *
     * @param taskType the type of task that is to be added.
     * @param name     he name of the task, which is needed to instantiate the task.
     * @param time     the time of the task, which is needed to instantiate the task. ToDo tasks does not have
     *                 time attribute, so any Date can be passed in and will be ignored.
     * @param doAfter  task to be done after main task
     * @param tags     tag associated with the task
     * @param priority priority level of the task
     */
    TaskAddCommand(Task.TaskType taskType, String name, LocalDateTime time,
                   String doAfter, ArrayList<String> tags, String priority) {
        this.taskType = taskType;
        this.name = name;
        this.time = time;
        this.doAfter = doAfter;
        this.tags = tags;
        this.priority = priority;
    }

    /**
     * Executes the add command by instantiating the task first and then add the task to task list.
     *
     * @return a flag whether the task is successfully added. Returns false if the taskType is not
     *         recognised.
     */
    @Override
    public boolean execute() {
        TaskList taskList = Duke.getModel().getTaskList();
        Task task = prepareTaskByType();
        if (task == null) {
            return false;
        }
        String clashMsg = findClash(taskList, task);
        taskList.add(task);
        if (!silent) {
            constructAddCommandMessage(taskList, task, clashMsg);
        }
        return true;
    }

    private Task prepareTaskByType() {
        Task task;
        switch (taskType) {
        case ToDo:
            task = new ToDo(name, doAfter, tags, priority);
            break;
        case Deadline:
            task = new Deadline(name, time, doAfter, tags, priority);
            break;
        case Event:
            task = new Event(name, time, doAfter, tags, priority);
            break;
        default:
            task = null;
        }
        return task;
    }

    private String findClash(TaskList taskList, Task task) {
        TaskList clashTasks = taskList.findClash(task);
        String clashMsg = "";
        if (clashTasks.size() > 0) {
            clashMsg = "\n\nWarning: New task added clashes with other task(s) in the list.\n";
            clashMsg += clashTasks.toString();
        }
        return clashMsg;
    }

    private void constructAddCommandMessage(TaskList taskList, Task task, String clashMsg) {
        String msg = "Got it. I've added this task: \n";
        msg += "  " + task.toString() + "\n";
        msg += "Now you have " + taskList.size() + " task(s) in the list. ";
        msg += clashMsg;
        responseMsg = msg;
        Duke.getUI().showResponse(msg);
    }
}
