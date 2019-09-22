package wallet.model.task;

public class Todo extends Task {
    /**
     * Constructs a new Todo object.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Outputs the string with the correct format for printing to UI.
     *
     * @return The string formatted for printing to UI.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Outputs the string with the correct format for writing to output file.
     *
     * @return The string formatted for writing to output file.
     */
    @Override
    public String writeToFile() {
        return "T," + super.writeToFile();
    }
}