package Commands;
import DukeExceptions.DukeException;
import Tasks.*;
import Interface.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Represents the command to snooze a Task object to a TaskList object.
 */
public class SnoozeCommand extends Command{

    private final int index;
    private final String dateString;
    private final String start;
    private final String end;
    private String modCode;

    /**
     * Creates an SnoozeCommand object.
     * @param index The index representing the task number in the TaskList object
     */
    public SnoozeCommand(int index, String dateString, String start, String end){
        this.index = index;
        this.dateString = dateString;
        this.start = start;
        this.end = end;
    }

    /**
     * Executes the snoozing a task inside the TaskList object with the given number.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the find message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display snooze message
     */
    @Override
    public String execute(LookupTable LT,TaskList events, TaskList deadlines, Ui ui, Storage storage) throws DukeException, FileNotFoundException {
        TaskList list = new TaskList();
        ArrayList<Task> eventsList = events.getList();
        ArrayList<Task> deadlinesList = deadlines.getList();
        for (Task task : eventsList) {
            list.addTask(task);
        }
        for (Task task : deadlinesList) {
            list.addTask(task);
        }
        if (end.equals(dateString)) {
            list.snoozeTask(deadlinesList, index, dateString, start, dateString);

            storage.updateDeadlineList(deadlines);
            return ui.showSnooze(index, deadlinesList.size(), deadlinesList);
        } else {
            list.snoozeTask(eventsList, index, dateString, start, end);
            storage.updateEventList(events);
            return ui.showSnooze(index, eventsList.size(), eventsList);
        }
    }
}