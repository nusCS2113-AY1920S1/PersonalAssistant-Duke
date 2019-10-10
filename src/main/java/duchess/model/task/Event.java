package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duchess.exceptions.DuchessException;
import duchess.model.TimeFrame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;

public class Event extends Task {
    private LocalDateTime end;
    private LocalDateTime start;

    /**
     * Create an event task from user input.
     *
     * @param input tokenized user input
     * @throws DuchessException error if user input is invalid
     */
    public Event(List<String> input) throws DuchessException {
        int separatorIndex = input.indexOf("/at");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DuchessException("Format for event: event <event> /at <start datetime> to <end datetime>");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            this.description = String.join(" ", input.subList(0, separatorIndex));
            String strStart = String.join(" ", input.subList(separatorIndex + 1, separatorIndex + 3));
            String strEnd = String.join(" ", input.subList(separatorIndex + 4, separatorIndex + 6));
            this.start = LocalDateTime.parse(strStart, formatter);
            this.end = LocalDateTime.parse(strEnd, formatter);
            if (end.isBefore(start)) {
                throw new DuchessException("Start datetime cannot be after end datetime.");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Format for event: event <event> /at <start datetime> to <end datetime>");
        } catch (DateTimeParseException e) {
            throw new DuchessException("Invalid datetime. Correct format: dd/mm/yyyy hhmm");
        }
    }

    @Override
    public TimeFrame getTimeFrame() {
        return new TimeFrame(start, end);
    }

    @Override
    public void snooze() {
        start = start.plusWeeks(1);
        end = end.plusWeeks(1);
    }

    @Override
    public Optional<Task> getReminder() {
        return Optional.of(this);
    }

    /**
     * Constructor for Jackson.
     *
     * @param start start time
     * @param end   end time
     */
    @JsonCreator
    public Event(
            @JsonProperty("start") String start,
            @JsonProperty("end") String end
    ) {
        this.start = LocalDateTime.parse(start);
        this.end = LocalDateTime.parse(end);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                .withResolverStyle(ResolverStyle.STRICT);
        return String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description,
                formatter.format(this.start), formatter.format(this.end));
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonGetter("end")
    public String getEnd() {
        return end.toString();
    }

    @JsonGetter("start")
    public String getStart() {
        return start.toString();
    }
}