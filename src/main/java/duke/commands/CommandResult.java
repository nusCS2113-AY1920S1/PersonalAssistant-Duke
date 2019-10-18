package duke.commands;

import duke.model.TaskList;
import duke.model.events.Task;
import duke.model.locations.BusStop;
import duke.model.locations.Venue;

import java.util.ArrayList;

public class CommandResult {
    private String message;
    private boolean isExit;
    private boolean isMap;
    private boolean isCalendar;
    private TaskList tasks;
    private ArrayList<Venue> route;

    /**
     * Constructs a basic CommandResult object.
     *
     * @param message Message for ui to display.
     */
    public CommandResult(String message) {
        this.message = message;
        isExit = false;
        isMap = false;
        isCalendar = false;
    }

    /**
     * Alternative constructor that helps to create text for a list of tasks.
     */
    public CommandResult(TaskList tasks) {
        message = "Here are the list of tasks:\n";
        int i = 1;
        for (Task t : tasks) {
            message += (i + ". " + t + "\n");
            i += 1;
        }
    }

    public ArrayList<Venue> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Venue> route) {
        this.route = route;
    }

    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    public TaskList getTasks() {
        return tasks;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public void setMap(boolean map) {
        isMap = map;
    }

    public void setCalendar(boolean calendar) {
        isCalendar = calendar;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isMap() {
        return isMap;
    }

    public boolean isCalendar() {
        return isCalendar;
    }

    @Override
    public String toString() {
        return message;
    }
}
