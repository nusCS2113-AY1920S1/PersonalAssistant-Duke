package task;

import exception.DukeException;
import parser.Parser;

public class Fixed extends Task {
    private String type = "F";
    private String duration;

    /**
     * Event initialization from String containing description and duration.
     *
     * @param description String which contains description and duration
     * @throws DukeException DukeException thrown when invalid number of arguments are passed
     */
    public Fixed(String description) throws DukeException {
        String[] split = description.split(Parser.fixed);
        if (split.length < 2) {
            System.out.println("I threw exception");
            throw new DukeException("Please use /need to indicate the window to complete this task");
        } else if (split.length > 2) {
            throw new DukeException("Too many /need in String");
        } else {
            this.description = split[0];
            this.duration = split[1];
            this.isDone = false;
        }
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String should be 1 or 0, describes if the Task is done or not
     * @param description String contains description of Task
     * @param between     String contains duration for the task
     */
    public Fixed(String bool, String description, String between) throws DukeException {
        this.description = description;
        this.duration = between;
        this.isDone = (1 == Integer.parseInt(bool));
    }

    /**
     * Returns Task in print friendly format.
     *
     * @return String which contains Task Type icon, status and Description and duration of task
     */
    @Override
    public String toList() {
        return "[F][" + this.getStatusIcon() + "] " + this.getDescription()
                + " (Need: " + this.duration + ")";
    }

    /**
     * Returns type of Task.
     *
     * @return String consisting of a single Letter (for now)
     */
    @Override
    public String getType() {
        return "F";
    }

    /**
     * Returns the time needed to complete this task.
     *
     * @return String containing the duration needed as defined by the user
     */
    @Override
    public String getAfter() {
        return this.duration;
    }
}

