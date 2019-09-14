package duke.task;

import duke.dukeexception.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Event extends Task {
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
        formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        formatter.setLenient(false);
        int separatorIndex = input.indexOf("/at");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for event: event <event> /at <start datetime> to <end> datetime");
        }
        try {
            this.description = String.join(" ", input.subList(0, separatorIndex));
            String strStart = String.join(" ", input.subList(separatorIndex + 1, separatorIndex + 3));
            String strEnd = String.join(" ", input.subList(separatorIndex + 4, separatorIndex + 6));
            this.start = formatter.parse(strStart);
            this.end = formatter.parse(strEnd);

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
    public String toString() {
        return String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description,
                formatter.format(this.start), formatter.format(this.end));
    }
}
