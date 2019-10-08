package Commands;

import Interface.Duke;
import Interface.Storage;
import Interface.Ui;
import Tasks.Event;
import Tasks.TaskList;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Handles Commnand to plan Tentative Schedule
 */
public class TentativeSchedulingCommand extends Command{
    private final TaskList tentativeDates = Duke.getTentativeDates();
    private final String description;
    private final ArrayList<String> dateString;
    private final ArrayList<String> startTimeString;
    private final ArrayList<String> endTimeString;
    /**
     * Creates a Tentative Scheduling object.
     * @param des Description of event
     * @param dateString Array of dates of events
     * @param endTimeString Array of endTimes of events
     * @param startTimeString Array of startTime of events
     */
    public TentativeSchedulingCommand(String des,ArrayList<String> dateString, ArrayList<String> startTimeString, ArrayList<String> endTimeString) throws FileNotFoundException {
        this.description = des;
        this.dateString = dateString;
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;
        for (int i = 0; i < dateString.size();i++){
            Event event = new Event(description, dateString.get(i),startTimeString.get(i),endTimeString.get(i), null);
            this.tentativeDates.addTask(event);
        }
        Storage.updateTentativeDates(tentativeDates);
    }
    /**
     * Executes the finding of earliest available block period inside the TaskList object with the given duration.
     * @param ui The Ui object to display the earliest free time message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display Tentative Schedule
     */
    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) {
        return ui.showTentativeSchedule(this.tentativeDates);
    }
}
