package seedu.duke.task;

import seedu.duke.common.parser.CommandParseHelper;
import seedu.duke.task.command.TaskParseNaturalDateHelper;
import seedu.duke.task.entity.Deadline;
import seedu.duke.task.entity.Event;
import seedu.duke.task.entity.Task;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * TaskList class is the special type of ArrayList that contains Task and has some special functions used to
 * manipulate the tasks in this list.
 */
public class TaskList extends ArrayList<Task> {

    /**
     * Converts the task list to a string of the pre-determined format that is ready to be displayed by the
     * UI.
     *
     * @return message that lists all tasks in the list
     */
    @Override
    public String toString() {
        if (this.size() == 0) {
            return "There is nothing in your task list.";
        }
        String msg = "Here are the tasks in your task list (total of " + this.size() + "):";
        for (int i = 0; i < this.size(); i++) {
            msg += System.lineSeparator() + (i + 1);
            msg += ". " + this.get(i);
        }
        return msg;
    }

    /**
     * Searches through all the tasks in the list to look for the keyword specified. The result will be the
     * string containing all the task string that is ready to be displayed.
     *
     * @param keyword the target keyword for searching
     * @return a string containing all the tasks found and is ready to be displayed by UI
     */
    public String findKeyword(String keyword) {
        ArrayList<Task> searchResult = prepareSearchResult(keyword);
        return constructSearchMessage(searchResult);
    }

    /**
     * Constructs search message.
     *
     * @param searchResult list of tasks
     * @return search message
     */
    private String constructSearchMessage(ArrayList<Task> searchResult) {
        String msg = "";
        if (searchResult.size() > 0) {
            msg += "Here are the matching tasks in your list:";
            for (int i = 0; i < searchResult.size(); i++) {
                msg += System.lineSeparator() + (i + 1) + ". " + searchResult.get(i);
            }
        } else {
            msg += "There is no matching task in your list.";
        }
        return msg;
    }

    /**
     * Prepares search result of keyword in task list.
     *
     * @param keyword to be searched.
     * @return list of task in which the keyword is found
     */
    private ArrayList<Task> prepareSearchResult(String keyword) {
        ArrayList<Task> searchResult = new ArrayList<>();
        for (Task task : this) {
            if (task.matchKeyword(keyword)) {
                searchResult.add(task);
            }
        }
        return searchResult;
    }

    /**
     * Marks the task at the specified index in the task list as done.
     *
     * @param index the target index of which the task is to be marked as done
     * @return a message that is ready to be displayed by UI
     * @throws CommandParseHelper.CommandParseException an exception thrown when index parsing failed or out
     *                                                  of range
     */
    public String markDone(int index) throws CommandParseHelper.CommandParseException {
        if (index < 0 || index >= this.size()) {
            throw new CommandParseHelper.CommandParseException("Invalid Index");
        }
        this.get(index).markDone();
        return constructDoneMessage(index);
    }

    /**
     * Constructs done message.
     *
     * @param index of task done
     * @return done message
     */
    private String constructDoneMessage(int index) {
        String msg = "Nice! I've marked this task as done:" + System.lineSeparator();
        msg += this.get(index).toString() + System.lineSeparator();
        msg += "Now you have " + this.size()
                + " tasks in the list.";
        return msg;
    }

    /**
     * Deletes the task at the specified index in the task list.
     *
     * @param index the target index of which the task is to be deleted
     * @return a message that is ready to be displayed by UI
     * @throws CommandParseHelper.CommandParseException an exception thrown when index parsing failed or out
     *                                                  of range
     */
    public String delete(int index) throws CommandParseHelper.CommandParseException {
        validateIndex(index);
        Task deleted = this.remove(index);
        return constructDeleteMessage(deleted);
    }

    /**
     * Constructs delete message.
     *
     * @param deleted task deleted
     * @return delete message
     */
    private String constructDeleteMessage(Task deleted) {
        String msg = "Noted. I've removed this task:" + System.lineSeparator();
        msg += deleted + System.lineSeparator();
        msg += "Now you have " + this.size() + " tasks in the list.";
        return msg;
    }

    /**
     * Finds all the tasks that are considered as near.
     *
     * @param dayLimit the maximum number of days from now for a task to be considered as near
     * @return whether the task is near
     */
    public TaskList findNear(int dayLimit) {
        TaskList nearTasks = new TaskList();
        for (Task t : this) {
            if (t.isNear(dayLimit)) {
                nearTasks.add(t);
            }
        }
        return nearTasks;
    }

    /**
     * Snooze or delay the tasks.
     *
     * @param index of the task in taskList.
     * @return string msg.
     */
    public String snoozed(int index, int duration) throws CommandParseHelper.CommandParseException {
        validateIndex(index);
        Task task = this.get(index);
        return constructSnoozeMessage(task, duration, index);
    }

    /**
     * Constructs snooze message.
     *
     * @param task to be snoozed
     * @param duration of snooze
     * @param index of task
     * @return snooze message
     */
    private String constructSnoozeMessage(Task task, int duration, int index) {
        if (task.getTaskType() != Task.TaskType.TODO) {
            task.snooze(duration);
            return "Noted. I've snoozed task " + (index + 1) + " by " + duration + " days";
        } else {
            return "This task cannot be snoozed";
        }
    }

    /**
     * Adds or modifies task to include a 'do after' task.
     *
     * @param index       Position of task in list
     * @param description Name of task
     * @return confirmation message that do after task has been added
     * @throws CommandParseHelper.CommandParseException when input is in wrong format
     */
    public String setDoAfter(int index, String description) throws CommandParseHelper.CommandParseException {
        validateIndex(index);
        Task task = this.get(index);
        task.setDoAfterDescription(description);
        return constructDoAfterMessage(description, index + 1);
    }

    /**
     * Constructs do after message.
     *
     * @param description for do after
     * @param size of task
     * @return do after message
     */
    private String constructDoAfterMessage(String description, int size) {
        return "Do after task " + description + " has been added to task " + size;
    }

    /**
     * Modifies task time.
     *
     * @param index       Position of task in list
     * @param description Name of task
     * @return confirmation message that do after task has been added
     * @throws CommandParseHelper.CommandParseException when input is in wrong format
     */
    public String setTime(int index, String description) throws CommandParseHelper.CommandParseException {
        validateIndex(index);
        setTimeByType(description, this.get(index));
        return constructSetTimeMessage(index, description);
    }

    /**
     * Sets time by task type.
     *
     * @param description of set time
     * @param task task to be set
     * @throws CommandParseHelper.CommandParseException if input is invalid
     */
    private void setTimeByType(String description, Task task) throws CommandParseHelper.CommandParseException {
        if (task.getTaskType() == Task.TaskType.DEADLINE) {
            ((Deadline) task).setTime(TaskParseNaturalDateHelper.getDate(description));
        } else if (task.getTaskType() == Task.TaskType.EVENT) {
            ((Event) task).setTime(TaskParseNaturalDateHelper.getDate(description));
        }
    }

    /**
     * Construct set time message.
     *
     * @param index of email
     * @param description of set time
     * @return set time message
     */
    private String constructSetTimeMessage(int index, String description) {
        return "Time for task " + (index + 1) + " has been changed to " + description;
    }

    /**
     * Generates a list of string containing all tasks in string form.
     *
     * @return a list of task string
     */
    public ArrayList<String> getTaskGuiStringList() {
        ArrayList<String> stringList = new ArrayList<>();
        for (Task task : this) {
            stringList.add(task.toString());
        }
        return stringList;
    }

    /**
     * Adds or modifies task to include a priority level.
     *
     * @param index    Position of task in list
     * @param priority Priority level of task
     * @return Confirmation message that priority level has been added
     * @throws CommandParseHelper.CommandParseException when input is in wrong format
     */
    public String setPriority(int index, Task.Priority priority) throws CommandParseHelper.CommandParseException {
        validateIndex(index);
        Task task = this.get(index);
        task.setPriorityLevelTo(priority);
        return constructSetPriorityMessage(priority, index + 1);
    }

    /**
     * Validates a index within the bound of size of task list.
     *
     * @param index index of email
     * @throws CommandParseHelper.CommandParseException if index out of bound
     */
    private void validateIndex(int index) throws CommandParseHelper.CommandParseException {
        if (index < 0 || index >= this.size()) {
            throw new CommandParseHelper.CommandParseException("Invalid index");
        }
    }

    private String constructSetPriorityMessage(Task.Priority priority, int size) {
        return "Priority of task " + size + " is set to " + priority.name();
    }

    /**
     * Adds or modifies tags of a task.
     *
     * @param index Position of task in list
     * @param tags  Priority level of task
     * @return Confirmation message that tags have been added
     * @throws CommandParseHelper.CommandParseException when input is in wrong format
     */
    public String setTags(int index, ArrayList<String> tags) throws CommandParseHelper.CommandParseException {
        validateIndex(index);
        Task task = this.get(index);
        task.setTags(tags);
        return constructSetTagsMessage(index + 1);
    }

    /**
     * Constructs set tags message.
     *
     * @param size size of tag list
     * @return set Tags message
     */
    private String constructSetTagsMessage(int size) {
        return "Tags of task " + size + " has been updated";
    }

    /**
     * Detect if a task being added clashes with another task in the list.
     *
     * @param task task to be added.
     * @return TaskList containing tasks that clashes with the new task being added.
     */
    public TaskList findClash(Task task) {
        TaskList clashTasks = new TaskList();
        for (Task t : this) {
            if (t.isClash(task)) {
                clashTasks.add(t);
            }
        }
        return clashTasks;
    }

    /**
     * Clears the task list by deleting the task one by one.
     *
     * @return a string message to be printed
     */
    public String clearList() {
        if (this.size() == 0) {
            return "The task list has already been cleared";
        } else {
            while (this.size() != 0) {
                this.remove(0);
            }
        }
        return constructClearListMessage();
    }

    /**
     * Constructs clear list message.
     *
     * @return clear list message
     */
    private String constructClearListMessage() {
        return "Task List has been cleared";
    }

    /**
     * Sorts task list by priority.
     */
    public void sortListByPriority() {
        sort(Comparator.comparing(Task::getPriority));
    }
}
