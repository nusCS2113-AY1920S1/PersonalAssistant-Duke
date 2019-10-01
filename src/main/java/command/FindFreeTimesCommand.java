package command;

import exception.DukeException;
import storage.Storage;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class FindFreeTimesCommand extends Command {
    private int hours;
    private ArrayList<Event> scheduleList;
    private Date freeTime;
    private Date dateNow;

    /**
     * Command to find the next free n number of hours in the schedule.
     * @param splitStr from user
     */
    public FindFreeTimesCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1) {
            throw new DukeException("☹ OOPS!!! Please input the number of hours of free time you need");
        }
        try {
            hours = Integer.parseInt(splitStr[1]);
        } catch (NumberFormatException e) {
            throw new DukeException("☹ OOPS!!! Number of hours needs to be an integer!");
        }
        if (hours < 1) {
            throw new DukeException("☹ OOPS!!! Please enter a number > 0");
        }
        scheduleList = new ArrayList<>();
        dateNow = new Date(System.currentTimeMillis());
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        for (Task i : tasks) {
            if (i.getClass() == Event.class) {
                Event temp = (Event) i;
                if (temp.getDateTimeStart().after(dateNow) || temp.getDateTimeEnd().after(dateNow)) {
                    scheduleList.add((Event) i);
                }
            }
        }
        scheduleList.sort(
                Comparator.comparing(Task::getDateTime));
        Date timeUntil = addHoursToDate(dateNow, this.hours);

        if (scheduleList.isEmpty()) {
            freeTime = dateNow;
        } else if (scheduleList.size() == 1) {
            Event onlyEvent = scheduleList.get(0);
            if (timeUntil.before(onlyEvent.getDateTimeStart())) {
                freeTime = dateNow;
            } else if (dateNow.after(onlyEvent.getDateTimeEnd())) {
                freeTime = dateNow;
            } else {
                freeTime = onlyEvent.getDateTimeEnd();
            }
        } else {
            Date currTime = dateNow;
            boolean found = false;
            for (int i = 1; i < scheduleList.size(); i++) {
                timeUntil = addHoursToDate(currTime, hours);
                Event first = scheduleList.get(i - 1);
                Event second = scheduleList.get(i);
                if (currTime.after(first.getDateTimeEnd()) && timeUntil.before(second.getDateTimeStart())) {
                    freeTime = currTime;
                    found = true;
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                Event last = scheduleList.get(scheduleList.size() - 1);
                freeTime = last.getDateTimeEnd();
            }
        }
        ui.showString("Next available time slot is: " + freeTime.toString());
    }

    public Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

}
