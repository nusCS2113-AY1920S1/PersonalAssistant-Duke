package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import duchess.exceptions.DuchessException;
import duchess.model.TimeFrame;

import java.util.Optional;

public class Todo extends Task {

    public Todo(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[T]%s %s", super.toString(), this.description);
    }

    @Override
    public boolean isCalendarEntry() {
        return TimeFrame.ofTimelessTask().hasDuration();
    }

    @Override
    public TimeFrame getTimeFrame() {
        return TimeFrame.ofTimelessTask();
    }

    @Override
    public void snooze() throws DuchessException {
        throw new DuchessException("You can't snooze that task.");
    }

    @Override
    public Optional<Task> getReminder() {
        return Optional.empty();
    }

    @JsonCreator
    public Todo() {
    }
}