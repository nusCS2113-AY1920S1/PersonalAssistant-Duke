package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import duchess.model.TimeFrame;
import duchess.parser.Util;

import java.time.LocalDateTime;
import java.util.Optional;

public class Deadline extends Task {
    private LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) {
        this.description = description;
        this.deadline = deadline;
    }

    @JsonCreator
    public Deadline(@JsonProperty("deadline") String deadline) {
        this.deadline = LocalDateTime.parse(deadline);
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