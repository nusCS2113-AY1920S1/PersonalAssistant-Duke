package duke.modules;

import duke.exceptions.ModInvalidTimePeriodException;

import java.time.LocalDateTime;

public class Todo extends Task {

    /**
     * constructor for the Task class.
     * @param input User input task name.
     */
    public Todo(String... input) throws ModInvalidTimePeriodException {
        super(input[0]);
    }

    @Override
    public String writingFile() {
        return "T" + "|" + super.writingFile();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
