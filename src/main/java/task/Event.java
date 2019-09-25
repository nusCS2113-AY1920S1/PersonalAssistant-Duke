package task;

import exception.DukeException;
import parser.Parser;

import java.util.Arrays;

public class Event extends Task {
    private String type = "E";

    /**
     * Event initialization from String containing description and Date.
     *
     * @param description String which contains description and date
     * @throws DukeException DukeException thrown when invalid number of arguments are passed
     */
    public Event(String description) throws DukeException {
        String[] split = description.split(Parser.event);
        //String[] split1 = description.split(" ");
        if (split.length < 2) {
            throw new DukeException("Please use /at to indicate date");
        } else {
            this.isDone = false;
            this.description = split[0];
            this.readDate(Arrays.copyOfRange(split, 1, split.length));
        }
        /*        else if (split.length > 2) {
            throw new DukeException("Too many /at in String");
        }*/
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String should be 1 or 0, describes if the Task is done or not
     * @param description String contains description of Task
     * @param dueDate     String contains the date in correct format
     */
    public Event(String bool, String description, String dueDate) throws DukeException {
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
        return "[E][" + this.getStatusIcon() + "] " + this.getDescription()
                + " (at: " + this.getDueDate() + ")";
    }

    /**
     * sets a new date for the due date.
     *
     * @param postponeDetails are details of when to postpone.
     * @throws DukeException throws when details are incorrect.
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
        return "E";
    }
}
