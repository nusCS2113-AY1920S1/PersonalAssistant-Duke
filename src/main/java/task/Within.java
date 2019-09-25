package task;

import exception.DukeException;
import parser.Parser;

public class Within extends Task {
    private String type = "W";
    private String between;

    /**
     * Event initialization from String containing description and task completion window.
     *
     * @param description String which contains description and task completion window
     * @throws DukeException DukeException thrown when invalid number of arguments are passed
     */
    public Within(String description) throws DukeException {
        String[] split = description.split(Parser.within);
        if (split.length < 2) {
            System.out.println("I threw exception");
            throw new DukeException("Please use /between to indicate the window to complete this task");
        } else if (split.length > 2) {
            throw new DukeException("Too many /between in String");
        } else {
            this.description = split[0];
            this.between = split[1];
            this.isDone = false;
        }
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String should be 1 or 0, describes if the Task is done or not
     * @param description String contains description of Task
     * @param between     String contains the time window to complete this task
     */
    public Within(String bool, String description, String between) throws DukeException {
        this.description = description;
        this.between = between;
        this.isDone = (1 == Integer.parseInt(bool));
    }

    /**
     * Returns Task in print friendly format.
     *
     * @return String which contains Task Type icon, status and Description and time window if any
     */
    @Override
    public String toList() {
        return "[W][" + this.getStatusIcon() + "] " + this.getDescription()
                + " (Between: " + this.between + ")";
    }

    /**
     * Returns type of Task.
     *
     * @return String consisting of a single Letter (for now)
     */
    @Override
    public String getType() {
        return "W";
    }

    /**
     * Returns the time window to complete this task.
     *
     * @return String containing the window as defined by the user
     */
    @Override
    public String getAfter() {
        return this.between;
    }
}

