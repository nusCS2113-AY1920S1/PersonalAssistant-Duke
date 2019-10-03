package duke.tasks;

/**
 * A class inheriting from duke.tasks.Task used to represent tasks that have both a description and a
 * task it is supposed to be after.
 */
public class DoAfter extends Task {

    private int previousTaskNumber;
    private int currentNumber;

    /**
     * Constructor for the duke.tasks.DoAfter object, which consists of the description of a task and a
     * before task that is associated with it.
     * @param description The description of the task.
     * @param previousTaskNumber The number of the before task associated with it with respect to the task list.
     * @param currentNumber The number of the task itself with respect to the task list.
     */
    public DoAfter(String description, int previousTaskNumber, int currentNumber) {
        super(description);
        this.previousTaskNumber = previousTaskNumber;
        this.currentNumber = currentNumber;
    }

    /**
     * Sets the previous task index to the specific integer.
     * @param previousTaskIndex The integer representing the position of the previous task.
     */
    public void setPreviousTaskNumber(int previousTaskIndex) {
        this.previousTaskNumber = previousTaskIndex;
    }

    /**
     * Returns the position of the DoAfter task.
     * @return The integer representing the position of the DoAfter task in the list.
     */
    public int getCurrentNumber() {
        return currentNumber;
    }

    /**
     * Sets the position of the DoAfter task to the specific integer.
     * @param currentNumber The integer that represents the position of the DoAfter task in the list.
     */
    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    /**
     * Returns a String representation of the duke.tasks.DoAfter object, displaying its type (duke.tasks.DoAfter),
     * description and the previous task associated with it.
     *
     * @return a String representation of the duke.tasks.DoAfter object
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (after: Task " + previousTaskNumber + ")";
    }
}
