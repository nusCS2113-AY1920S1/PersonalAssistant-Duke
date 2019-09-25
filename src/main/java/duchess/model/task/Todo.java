package duchess.model.task;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.Schedule;

import java.util.Date;
import java.util.List;

public class Todo extends Task {
    private String description;

    /**
     * Creates a todo task from given user input.
     *
     * @param input tokenized user input
     * @throws DukeException the error if user input is invalid
     */
    public Todo(List<String> input) throws DukeException {
        this.description = String.join(" ", input);
        if (input.size() == 0) {
            throw new DukeException("Format for todo: todo <task>");
        }
    }

    @Override
    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    @Override
    public String toString() {
        return String.format("[T]%s %s", super.toString(), this.description);
    }

    @Override
    public Schedule isWithinTimeFrame(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public void snooze() throws DukeException {
        throw new DukeException("You can't snooze that task.");
    }
}
