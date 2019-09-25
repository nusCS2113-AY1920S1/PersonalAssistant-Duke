package task;

import exception.DukeException;
import parser.Parser;

public class Recurring extends Task {
    private String type = "R";
    private String frequency;

    /**
     * Event initialization from String containing description and frequency.
     *
     * @param description String which contains description and frequency
     * @throws DukeException DukeException thrown when invalid number of arguments are passed
     */
    public Recurring(String description) throws DukeException {
        String[] split = description.split(Parser.recurring);
        if (split.length < 2) {
            System.out.println("I threw exception");
            throw new DukeException("Please use /every to indicate recurrence of task");
        } else if (split.length > 2) {
            throw new DukeException("Too many /every in String");
        } else {
            this.description = split[0];
            this.frequency = split[1];
            this.isDone = false;
        }
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String should be 1 or 0, describes if the Task is done or not
     * @param description String contains description of Task
     * @param frequency   String contains the frequency of this recurring task
     */
    public Recurring(String bool, String description, String frequency) throws DukeException {
        this.description = description;
        this.frequency = frequency;
        this.isDone = (1 == Integer.parseInt(bool));
    }

    /**
     * Returns Task in print friendly format.
     *
     * @return String which contains Task Type icon, status and Description and frequency if any
     */
    @Override
    public String toList() {
        return "[R][" + this.getStatusIcon() + "] " + this.getDescription()
                + " (Every: " + this.frequency + ")";
    }

    /**
     * Returns type of Task.
     *
     * @return String consisting of a single Letter (for now)
     */
    @Override
    public String getType() {
        return "R";
    }

    /**
     * Return the frequency of this task.
     *
     * @return String consisting of the user input
     */
    @Override
    public String getAfter() {
        return this.frequency;
    }
}
