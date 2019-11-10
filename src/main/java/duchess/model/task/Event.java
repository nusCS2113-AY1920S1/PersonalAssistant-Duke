package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duchess.exceptions.DuchessException;
import duchess.model.TimeFrame;
import duchess.model.calendar.CalendarUtil;
import duchess.parser.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class Event extends Task {
    private LocalDateTime end;
    private LocalDateTime start;

    /**
     * Creates an event task.
     *
     * @param description description of event task
     * @param end end time
     * @param start start time
     * @throws DuchessException if end time is before start time
     */
    public Event(String description, LocalDateTime end, LocalDateTime start) throws DuchessException {
        if (end.isBefore(start)) {
            throw new DuchessException("Start datetime cannot be after end datetime.");
        }
        this.description = description;
        this.end = end;
        this.start = start;
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
        return String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description,
                Util.formatDateTime(this.start), Util.formatDateTime(this.end));
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

    @Override
    public boolean isCalendarEntry() {
        LocalDate date = start.toLocalDate();
        return CalendarUtil.processDate(date) == 1 || CalendarUtil.processDate(date) == 2;
    }
}