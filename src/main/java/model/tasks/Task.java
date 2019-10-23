package model.tasks;

import model.members.Member;
import utils.DukeException;

import java.util.ArrayList;
import java.util.Date;

/**
 * a general Task class, to be extended
 */

public abstract class Task {

    public static ArrayList<Task> tasks;

    /**
     * description of the task
     */
    protected String description;

    /**
     * status of the task
     */
    protected boolean isDone;
    protected ArrayList<Task> precondition;

    /**
     * @author Justin Chia
     * Flag for recurring status
     */
    protected boolean isRecurring;

    /**
     * @author Justin Chia
     * Number of weeks for recurrence
     */
    protected int recurringWeeks;

    protected ArrayList<Member> pics;


    /**
     * default constructor of Task
     */
    public Task() {
        this.isDone = false;
        this.isRecurring = false;
        this.recurringWeeks = 0;
        this.precondition = new ArrayList<Task>();
        pics = new ArrayList<Member>();
    }

    /**
     * another constructor of Task
     *
     * @param description the description, or the content of a task
     *                    params isDone and recurring are auto set to false
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.isRecurring = false;
        this.recurringWeeks = 0;
        this.precondition = new ArrayList<Task>();
        pics = new ArrayList<Member>();
    }

    /**
     * get task's description
     *
     * @return task's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get tasks's isDone status
     *
     * @return isDone boolean
     */
    public boolean getIsDone() {
        return isDone;
    }

    public ArrayList<Member> getPics() {
        return pics;
    }

    public void setPics(ArrayList<Member> pics) {
        this.pics = pics;
    }

    /**
     * set the description of task
     *
     * @param description the description, or the content of a task
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Set the time of task to a certain date.
     * For TODO task, this method is unneeded.
     *
     * @param data data to set
     */
    public void setTime(Date data) {
        //for polymorphism use
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] "
                + this.description
                + ((this.precondition.size() == 0) ? "" : (" (Precondition: " + this.getPrecondition() + ")"));
    }

    /**
     * set start time of Period task
     *
     * @param start start time
     */
    public void setStart(Date start) {
    }


    /**
     * set end time of Period task
     *
     * @param end end time
     */
    public void setEnd(Date end) {
    }


    /**
     * set duration for Last task
     *
     * @param duration duration time
     */
    public void setDuration(String duration) {
    }


    /**
     * This method marks the task status as 'done'
     *
     * @throws DukeException if the preconditions (if it has) are not satisfied,
     *                       that is, this task cannot be marked as 'done' at this point
     */
    public void markAsDone() throws DukeException {
        boolean preconditionDone = true;
        String notDonePrecondition = "";
        for (int i = 0; i < precondition.size(); i++) {
            if (!precondition.get(i).isDone) {
                preconditionDone = false;
                notDonePrecondition += precondition.get(i) + "\n";
            }
        }
        if (!preconditionDone) {
            throw new DukeException("Duke error, the following precondition is not done: \n" + notDonePrecondition);
        }
        this.isDone = true;
    }

    /**
     * Return the status icon.
     *
     * @return the status icon ("V" for done and "x" for todo) of the task
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2715"); //return tick or X symbols
    }

    /**
     * @param numWeeks the number (quantity) of weeks for the recurrence task
     * @author Justin Chia
     * Toggle the recurrence flag
     */
    public void setRecurring(int numWeeks) {
        this.isRecurring = true;
        this.recurringWeeks = numWeeks;
    }

    /**
     * This abstract method return the String for saving the task object in txt file.
     *
     * @return String for saving the task object in txt file
     */
    public abstract String dataString();

    /**
     * This method add prerequisite task to the precondition list
     *
     * @param precondition the prerequisite task to be added
     */
    public void addPrecondition(Task precondition) {
        this.precondition.add(precondition);
    }

    /**
     * This method add prerequisite task to the precondition list
     *
     * @param preconditionString the prerequisite task to be added, represented by indexes of prerequisite tasks,
     *                           separated by space, for example: 1 2 10 11
     * @throws DukeException when the format of preconditions is not valid
     */
    public void addPrecondition(String preconditionString) throws DukeException {
        preconditionString = preconditionString.trim();
        if (preconditionString.equals("0")) {
            return;
        }
        if (preconditionString.trim().length() != 0) {
            String[] indexString = preconditionString.split(" ");
            int[] preconditionIndex = new int[indexString.length];
            for (int i = 0; i < preconditionIndex.length; i++) {
                try {
                    preconditionIndex[i] = Integer.parseInt(indexString[i].trim());
                } catch (NumberFormatException e) {
                    throw new DukeException("Prerequisite task sequence number format error, should be integer.");
                }
            }
            for (int i = 0; i < preconditionIndex.length; i++) {
                if (preconditionIndex[i] <= 0 || preconditionIndex[i] > tasks.size()) {
                    throw new DukeException("Prerequisite task sequence number out of range.");
                }
                this.addPrecondition(tasks.get(preconditionIndex[i] - 1));
            }
        }
    }

    /**
     * This method gets prerequisite task to the precondition list.
     *
     * @return the string of precondition, represented by indexes of prerequisite tasks,
     * separated by space, for example: 1 2 10 11
     */
    public String getPrecondition() {
        if (precondition.size() == 0) {
            return "0";
        }
        String preconditionString = "";
        for (int i = 0; i < precondition.size(); i++) {
            preconditionString += tasks.indexOf(precondition.get(i)) + 1 + " ";
        }
        return preconditionString;
    }
}
