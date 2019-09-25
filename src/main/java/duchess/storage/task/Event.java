package duchess.storage.task;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Event extends Task implements Snoozeable {
    private String description;
    private Date end;
    private Date start;
    private SimpleDateFormat formatter;

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

        formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
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

    /**
     * Checks if the event being added clashes with this instance of event.
     *
     * @param event the event task to be added
     * @return true if the event clashes, false otherwise
     */
    public boolean clashesWith(Event event) {
        return (startClashes(event) || endClashes(event)
                || entireEventClashes(event) || isStartOrEndEqual(event));
    }

    private boolean startClashes(Event event) {
        return event.start.after(this.start) && event.start.before(this.end);
    }

    private boolean endClashes(Event event) {
        return event.end.after(this.start) && event.end.before(this.end);
    }

    private boolean entireEventClashes(Event event) {
        return event.start.before(this.start) && event.end.after(this.start);
    }

    private boolean isStartOrEndEqual(Event event) {
        return event.start.equals(this.start) || event.end.equals(this.end);
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
    public String toString() {
        return String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description,
                formatter.format(this.start), formatter.format(this.end));
    }

    @Override
    public Schedule isWithinTimeFrame(Date startDate, Date endDate) {
        if (start.compareTo(startDate) < 0 && end.compareTo(startDate) >= 0) { // starts before ends after that date
            return new Schedule(start, String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description,
                    formatter.format(this.start), formatter.format(this.end)),true);
        } else if (start.compareTo(startDate) >= 0 && start.compareTo(endDate) <= 0) { // starts during date
            return new Schedule(start, String.format("[E]%s %s", super.toString(), this.description));
        }
        return null;
    }
}
