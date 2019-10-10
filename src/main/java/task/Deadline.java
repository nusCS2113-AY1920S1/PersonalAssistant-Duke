package task;

import exception.DukeException;
import parser.Parser;

/**
 * This is a class that inherits from the Task class.
 * In addition to its parent's methods, it also has the ability to parse the input date.
 * Very similar to the myTasks.Event class.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Deadline extends Task {
    protected String type = "D";

    /**
     * Deadline initializes from String containing description and Date.
     *
     * @param description String which contains description and date
     * @throws DukeException DukeException thrown when invalid number of arguments are passed
     */
    public Deadline(String description) throws DukeException {
        String[] split = description.split(Parser.deadline);
        if (split.length < 2) {
            throw new DukeException("Please use /by to indicate date");
        } else if (split.length > 2) {
            throw new DukeException("Too many /by in String");
        } else {
            this.description = split[0];
            this.readDate(split[1]);
            this.isDone = false;
        }
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String should be 1 or 0, describes if the Task is done or not
     * @param description String contains description of Task
     * @param dueDate     String contains the date in correct format
     */
    public Deadline(String bool, String description, String dueDate) throws DukeException {
        this.description = description;
        this.readDate(dueDate);
        this.isDone = (1 == Integer.parseInt(bool));
    }


    /**
     * Returns Task in print friendly format.
     *
     * @return String which contains Task Type icon, status and Description and DueDate if any
     */
    @Override
    public String toList() {
        return "[D][" + this.getStatusIcon() + "] " + this.getDescription()
                + " (By: " + this.getDueDate() + ")";
    }

    /**
     * sets a new due date for the deadline task.
     *
     * @param postponeDetails is the details of the postponing.
     * @throws DukeException throws errors when format is incorrect.
     */
    @Override
    public void snooze(String postponeDetails) throws DukeException {
        String[] split = postponeDetails.split(Parser.postpone);
        if (split.length < 2) {
            throw new DukeException("Please use /to to indicate date");
        } else if (split.length > 2) {
            throw new DukeException("Too many /to in String");
        } else {
            this.readDate(split[1]);
        }
    }


    /**
     * Returns type of Task.
     *
     * @return String consisting of a single Letter (for now)
     */
    @Override
    public String getType() {
        return "D";
    }


}
