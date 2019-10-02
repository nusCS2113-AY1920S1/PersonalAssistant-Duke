package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;

public class Deadline extends Task {
    private LocalDateTime deadline;

    /**
     * Creates a deadline task from user input.
     *
     * @param input tokenized user input
     * @throws DukeException an error if user input is invalid
     */
    public Deadline(List<String> input) throws DukeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                .withResolverStyle(ResolverStyle.STRICT);
        int separatorIndex = input.indexOf("/by");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for deadline: deadline <task> /by <deadline>");
        }
        this.description = String.join(" ", input.subList(0, separatorIndex));
        String strDeadline = String.join(" ", input.subList(separatorIndex + 1, input.size()));
        try {
            this.deadline = LocalDateTime.parse(strDeadline, formatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid datetime. Correct format: dd/mm/yyyy hhmm");
        }
    }

    @Override
    public void snooze() {
        deadline = deadline.plusWeeks(1);
    }

    @Override
    public Optional<Task> getReminder() {
        return Optional.of(this);
    }

    @JsonCreator
    public Deadline(
            @JsonProperty("deadline") LocalDateTime deadline
    ) {
        this.deadline = deadline;
    }

    @Override
    public TimeFrame getTimeFrame() {
        return TimeFrame.ofInstantaneousTask(this.deadline);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                .withResolverStyle(ResolverStyle.STRICT);
        return String.format("[D]%s %s (by: %s)", super.toString(), this.description, formatter.format(this.deadline));
    }

    @JsonGetter("deadline")
    public LocalDateTime getDeadline() {
        return deadline;
    }
}