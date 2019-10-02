package commands;

import controlpanel.Storage;
import controlpanel.Ui;
import tasks.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemindersCommand extends Command {
    protected Date nowDate;
    private SimpleDateFormat simpleDateFormat;
    private String keyword;

    /**
     * The constructor to initialize a Reminders command object.
     * @param string The type of the command.
     */
    public RemindersCommand(String string) {
        this.keyword = string;
        this.nowDate = new Date();
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException {
        Date currDate = nowDate;
        String[] currDay = simpleDateFormat.format(currDate).split(" ");
        Date startDay = simpleDateFormat.parse(currDay[0] + " 0000");
        Date endDay = simpleDateFormat.parse(currDay[0] + " 2359");

        switch (keyword) {
        case "upcoming": {
            ui.appendToOutput(" Got it. Your Upcoming Reminders: \n");
            int counter = 1;
            for (Task t : tasks.getCheckList()) {
                Boolean isAfter = false;
                if (t instanceof Deadline) {
                    Date dueDate = ((Deadline) t).getDateBy();
                    isAfter = dueDate.after(endDay);
                } else if (t instanceof Events) {
                    Date dueDate = ((Events) t).getStartDateAt();
                    isAfter = dueDate.after(endDay);
                } else if (t instanceof ToDos) {
                    isAfter = true;
                } else if (t instanceof Periods) {
                    Date dueDate = ((Periods) t).getDateFrom();
                    isAfter = dueDate.after(endDay);
                } else if (t instanceof MultipleEvent) {
                    Date dueDate = ((MultipleEvent) t).getStartDateAt();
                    isAfter = (dueDate.after(endDay) && ((MultipleEvent) t).getChosenStatus());
                }
                if (isAfter && !t.getStatus()) {
                    ui.appendToOutput(" " + counter++ + "." + t.toString() + "\n");
                }
            }
            break;
        }
        case "past": {
            ui.appendToOutput(" Got it. Your Past Reminders: \n");
            int counter = 1;
            for (Task t : tasks.getCheckList()) {
                Boolean isBefore = false;
                if (t instanceof Deadline) {
                    Date dueDate = ((Deadline) t).getDateBy();
                    isBefore = dueDate.before(startDay);
                } else if (t instanceof Events) {
                    Date dueDate = ((Events) t).getEndDateAt();
                    isBefore = dueDate.before(startDay);
                } else if (t instanceof Periods) {
                    Date dueDate = ((Periods) t).getDateTo();
                    isBefore = dueDate.before(startDay);
                } else if (t instanceof MultipleEvent) {
                    Date dueDate = ((MultipleEvent) t).getEndDateAt();
                    isBefore = (dueDate.before(startDay) && ((MultipleEvent) t).getChosenStatus());
                }
                if (isBefore && !t.getStatus()) {
                    ui.appendToOutput(" " + counter++ + "." + t.toString() + "\n");
                }
            }
            break;
        }
        case "today": {
            ui.appendToOutput(" Got it. Today's Reminders: \n");
            int counter = 1;
            for (Task t : tasks.getCheckList()) {
                Boolean isToday = false;
                if (t instanceof Deadline) {
                    Date dueDate = ((Deadline) t).getDateBy();
                    isToday = (!dueDate.before(startDay) && !dueDate.after(endDay));
                } else if (t instanceof Events) {
                    Date startDate = ((Events) t).getStartDateAt();
                    Date endDate = ((Events) t).getEndDateAt();
                    isToday = (startDate.after(startDay) && startDate.before(endDay))
                            || (endDate.after(startDay) && endDate.before(endDay))
                            || (startDay.after(startDate) && endDay.before(endDate));
                } else if (t instanceof Periods) {
                    Date startDate = ((Periods) t).getDateFrom();
                    Date endDate = ((Periods) t).getDateTo();
                    isToday = (startDate.after(startDay) && startDate.before(endDay))
                            || (endDate.after(startDay) && endDate.before(endDay))
                            || (startDay.after(startDate) && endDay.before(endDate));
                } else if (t instanceof MultipleEvent) {
                    Date startDate = ((MultipleEvent) t).getStartDateAt();
                    Date endDate = ((MultipleEvent) t).getEndDateAt();
                    isToday = ((startDate.after(startDay) && startDate.before(endDay))
                            || (endDate.after(startDay) && endDate.before(endDay))
                            || (startDay.after(startDate) && endDay.before(endDate))
                            && ((MultipleEvent) t).getChosenStatus());
                }
                if (isToday && !t.getStatus()) {
                    ui.appendToOutput(" " + counter++ + "." + t.toString() + "\n");
                }
            }
            break;
        }
        default:
            break;
        }
    }
}
