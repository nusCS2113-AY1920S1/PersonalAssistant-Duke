package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duchess.exceptions.DuchessException;
import duchess.model.TimeFrame;
import duchess.parser.Util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Deadline extends Task {
    private LocalDateTime deadline;

    /**
     * Creates a deadline task from user input.
     *
     * @param input tokenized user input
     * @throws DuchessException an error if user input is invalid
     */
    public Deadline(List<String> input) throws DuchessException {
        int separatorIndex = input.indexOf("/by");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DuchessException("Format for deadline: deadline <task> /by <deadline>");
        }
        this.description = String.join(" ", input.subList(0, separatorIndex));
        this.deadline = Util.parseDateTime(input, separatorIndex + 1);
    }

    @Override
    public void snooze() {
        deadline = deadline.plusWeeks(1);
    }

    @Override
    public Optional<Task> getReminder() {
        return Optional.of(this);
    }

    @Override
    public TimeFrame getTimeFrame() {
        return TimeFrame.ofInstantaneousTask(this.deadline);
    }

    @Override
    public String toString() {
        return String.format(
                "[D]%s %s (by: %s)",
                super.toString(),
                this.description,
                Util.formatDateTime(this.deadline)
        );
    }

    @JsonSetter("deadline")
    public void setDeadline(String deadline) {
        this.deadline = LocalDateTime.parse(deadline);
    }

    @JsonGetter("deadline")
    public String getDeadline() {
        return deadline.toString();
    }
}