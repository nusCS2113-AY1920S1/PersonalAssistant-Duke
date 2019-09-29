package duchess.model.task;

import duchess.logic.commands.exceptions.DukeException;
import duchess.model.TimeFrame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Deadline extends Task {
    private String description;
    private Date deadline;
    private SimpleDateFormat formatter;

    /**
     * Creates a deadline task from user input.
     *
     * @param input tokenized user input
     * @throws DukeException an error if user input is invalid
     */
    public Deadline(List<String> input) throws DukeException {
        formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        formatter.setLenient(false);
        int separatorIndex = input.indexOf("/by");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for deadline: deadline <task> /by <deadline>");
        }
        this.description = String.join(" ", input.subList(0, separatorIndex));
        String strDeadline = String.join(" ", input.subList(separatorIndex + 1, input.size()));
        try {
            this.deadline = formatter.parse(strDeadline);
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

        date.setTime(deadline);
        date.add(Calendar.DAY_OF_MONTH, 7);
        deadline.setTime(date.getTimeInMillis());
    }

    @Override
    public List<Task> getReminders() {
        List<Task> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    @Override
    public String toString() {
        return String.format("[D]%s %s (by: %s)", super.toString(), this.description, formatter.format(this.deadline));
    }

    @Override
    public TimeFrame getTimeFrame() {
        return TimeFrame.ofInstantaneousTask(this.deadline);
    }
}