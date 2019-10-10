package task;

import exception.DukeException;
import parser.Parser;

public class After extends Task {
    private String type = "A";
    private String following;

    /**
     * Event initialization from String containing description and prerequisite.
     *
     * @param description String which contains description and prerequisite
     * @throws DukeException DukeException thrown when invalid number of arguments are passed
     */
    public After(String description) throws DukeException {
        String[] split = description.split(Parser.after);
        if (split.length < 2) {
            System.out.println("I threw exception");
            throw new DukeException("Please use /after to indicate what needs to be done before doing this task");
        } else if (split.length > 2) {
            throw new DukeException("Too many /after in String");
        } else {
            this.description = split[0];
            this.following = split[1];
            this.isDone = false;
        }
    }

    /**
     * Overloaded constructor which reads in a task from file.
     *
     * @param bool        String should be 1 or 0, describes if the Task is done or not
     * @param description String contains description of Task
     * @param following   String contains what needs to be done first before this task
     */
    public After(String bool, String description, String following) throws DukeException {
        this.description = description;
        this.following = following;
        this.isDone = (1 == Integer.parseInt(bool));
    }

    /**
     * Returns Task in print friendly format.
     *
     * @return String which contains Task Type icon, status and Description and prerequisite if any
     */
    @Override
    public String toList() {
        return "[A][" + this.getStatusIcon() + "] " + this.getDescription()
                + " (After: " + this.following + ")";
    }

    /**
     * Returns type of Task.
     *
     * @return String consisting of a single Letter (for now)
     */
    @Override
    public String getType() {
        return "A";
    }

    /**
     * Returns the prerequisite of the task.
     *
     * @return String containing prerequisite of the task defined by the user
     */
    @Override
    public String getAfter() {
        return this.following;
    }
}

