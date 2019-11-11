package commands;

import commons.DukeConstants;
import commons.Reminder;
import dukeexceptions.DukeException;
import commons.Storage;
import commons.UserInteraction;
import tasks.Assignment;
import tasks.TaskList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents the command to delete a Task object from a TaskList object.
 */
public class DeleteCommand extends Command {
    private Assignment task;
    private final String list;
    private TaskList listToChange;

    /**
     * Creates a DeleteCommand object.
     * @param task The task to be deleted
     * @param list The name of the TaskList that requires changing
     */
    public DeleteCommand(String list, Assignment task) {
        this.task = task;
        this.list = list;
    }

    /**
     * Executes the deletion of a task inside the TaskList object with the given index.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the delete task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display delete task message
     * @throws DukeException On ArrayList out of bound error
     */
    @Override
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage)
            throws DukeException {
        HashMap<String, HashMap<String, ArrayList<Assignment>>> eventMap = events.getMap();
        HashMap<String, HashMap<String, ArrayList<Assignment>>> deadlineMap = deadlines.getMap();
        if (list.equals(DukeConstants.EVENT_LIST)) {
            super.insideMapChecker(eventMap, task);
            events.removeTask(task);
            storage.updateEventList(events);
            listToChange = events;
        } else if (list.equals(DukeConstants.DEADLINE_LIST)) {
            super.insideMapChecker(deadlineMap, task);
            deadlines.removeTask(task);
            storage.updateDeadlineList(deadlines);
            listToChange = deadlines;
            Reminder reminder = storage.getReminderObject();
            HashMap<Date, Assignment> remindMap = reminder.getRemindMap();
            reminder.setDeadlines(deadlines);
            Set<Date> dateKeySet = remindMap.keySet();
            for (Date date : dateKeySet) {
                Assignment remindTask = remindMap.get(date);
                String remindTaskDescription = remindTask.getDescription();
                String taskDescription = task.getDescription();
                String remindTaskDate = remindTask.getDateTime();
                String taskDate = task.getDateTime();
                if (remindTaskDescription.equals(taskDescription) && remindTaskDate.equals(taskDate)) {
                    reminder.removeTimerTask(task, date, DukeConstants.NO_FIELD);
                }
            }
        }
        return ui.showDelete(task, listToChange.taskListSize());
    }
}
