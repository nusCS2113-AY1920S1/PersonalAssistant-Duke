package duchess.model.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Event extends Task {
    private String description;
    private Date end;
    private Date start;

    /**
     * Create an event task from user input.
     *
     * @param input tokenized user input
     * @throws DukeException error if user input is invalid
     */
    public Event(List<String> input) throws DukeException {
        int separatorIndex = input.indexOf("/at");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for event: event <event> /at <start datetime> to <end datetime>");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        formatter.setLenient(false);

        try {
            this.description = String.join(" ", input.subList(0, separatorIndex));
            String strStart = String.join(" ", input.subList(separatorIndex + 1, separatorIndex + 3));
            String strEnd = String.join(" ", input.subList(separatorIndex + 4, separatorIndex + 6));
            this.start = formatter.parse(strStart);
            this.end = formatter.parse(strEnd);
            if (end.before(start)) {
                throw new DukeException("Start datetime cannot be after end datetime.");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Format for event: event <event> /at <start datetime> to <end datetime>");
        } catch (ParseException e) {
            throw new DukeException("Invalid datetime. Correct format: dd/mm/yyyy hhmm");
        }
    }

    @Override
    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    @Override
    public void snooze() {
        Calendar date = Calendar.getInstance();

        date.setTime(start);
        date.add(Calendar.DAY_OF_MONTH, 7);
        start.setTime(date.getTimeInMillis());

        date.setTime(end);
        date.add(Calendar.DAY_OF_MONTH, 7);
        end.setTime(date.getTimeInMillis());
    }

    @Override
    public List<Task> getReminders() {
        List<Task> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    /**
     * Constructor for Jackson.
     *
     * @param description description
     * @param start       start time
     * @param end         end time
     */
    @JsonCreator
    public Event(
            @JsonProperty("description") String description,
            @JsonProperty("start") Date start,
            @JsonProperty("end") Date end
    ) {
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public TimeFrame getTimeFrame() {
        return new TimeFrame(this.start, this.end);
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        return String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description,
                formatter.format(this.start), formatter.format(this.end));
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonGetter("end")
    public Date getEnd() {
        return end;
    }

    @JsonGetter("start")
    public Date getStart() {
        return start;
    }
}