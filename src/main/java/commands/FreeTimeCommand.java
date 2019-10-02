package commands;

import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import tasks.Events;
import tasks.Task;
import tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FreeTimeCommand extends Command {

    private Date date;
    private int duration;
    private SimpleDateFormat simpleDateFormat;

    /**
     * The constructor which initializes a new free time command with date and duration time.
     *
     * @param specificDate the time would like to know is there free time.
     * @param time         the length of the desired duration.
     * @throws ParseException exception related to time parsing.
     */
    public FreeTimeCommand(String specificDate, int time) throws ParseException {
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy HHmm");
        date = simpleDateFormat.parse(specificDate);
        duration = time;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * computes a new date which is "n" hours after the "start".
     * @param start input date (old date).
     * @param n the number of hours passed.
     * @return a new date which is "n" hours after the "start".
     */
    private Date addNHours(Date start, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.HOUR, n);
        return calendar.getTime();
    }

    /**
     * This method execute the free-time command. Implements priority queue to store the start time and the end time of.
     * the events which happens after the given date (the time we need to get free time). Then, if the start.
     * time of one existing events is after the end time of the current desired duration, the system will show the.
     * desired duration as the available time slot.
     *
     * @param tasks   task list.
     * @param ui      user interface.
     * @param storage read or write the local file which stores the task list.
     * @throws DukeException  any duke exception.
     * @throws ParseException any duke exception.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, ParseException {
        ArrayList<Date> startTime = new ArrayList<>();
        ArrayList<Date> endTime = new ArrayList<>();
        for (Task t : tasks.getCheckList()) {
            if (t instanceof Events) {
                Date eventStartDate = simpleDateFormat.parse(((Events) t).getStartAt());
                Date eventEndDate = simpleDateFormat.parse(((Events) t).getEndAt());
                if (!startTime.isEmpty() && eventStartDate.before(startTime.get(startTime.size() - 1))) {
                    int position = startTime.size() - 2;
                    for (; position >= 0; position--) {
                        if (eventStartDate.after(startTime.get(position))) {
                            position++;
                            break;
                        }
                    }
                    startTime.add(position, eventStartDate);
                    endTime.add(position, eventEndDate);
                } else {
                    startTime.add(eventStartDate);
                    endTime.add(eventEndDate);
                }
            }
        }

        Date start = date;
        Date end = addNHours(start, duration);
        for (int i = 0; i < startTime.size(); i++) {
            Date eventStart = startTime.get(i);
            Date eventEnd = endTime.get(i);
            if (end.before(eventStart)) {
                break;
            } else {
                if (start.before(eventEnd)) {
                    start = eventEnd;
                    end = addNHours(start, duration);
                }
            }
        }
        ui.appendToOutput("  The nearest time slot: \n" + "    " + simpleDateFormat.format(start) + " ~ "
                + simpleDateFormat.format(end) + " is available\n");
    } //execute
} //class
