package javacake.commands;

import javacake.DukeException;
import javacake.Storage;
import javacake.TaskList;
import javacake.Ui;
import javacake.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FreeTimeCommand extends Command {
    int lowerTimeBound = 800;
    int upperTimeBound = 1700;

    public FreeTimeCommand(String str) {
        type = CmdType.FREETIME;
        input = str;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (input.length() == 8) {
            throw new DukeException("     ☹ OOPS!!! The description of a freetime cannot be empty.");
        }
        input = input.substring(9);
        try {
            int diff = Integer.parseInt(input);
            if (diff > 9) {
                throw new DukeException("Hour can only be <= 9 [0800 to 1700]");
            }
            diff *= 100;
            Date currDate = new Date();
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(currDate);
            int currTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);;
            ui.showMessage("Current Date: " + currDate.toString());

            ArrayList<Task> eventList = new ArrayList<>();
            for (Task task : tasks.getData()) {
                if (task.getTaskType() == Task.TaskType.EVENT) {
                    eventList.add(task);
                }
            }
            ViewScheduleCommand.sortTasksByDate(eventList);

            for (Task task : eventList) {
                Date compDate = task.getDateTime();
                if (compDate == null) {
                    continue;
                }
                //ui.showMessage("Task Date: " + compDate.toString());
                calendar.setTime(compDate);
                int compInt = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);

                if (currTime < lowerTimeBound) {
                    currTime = lowerTimeBound;
                    currDate = getAndSetDate(currDate, currTime / 100, 0, 0, 0);
                } else if (currTime > upperTimeBound) {
                    currTime = lowerTimeBound;
                    currDate = getAndSetDate(currDate, currTime / 100, 0, 0, 1);
                    //ui.showMessage("Setting to Available: " + currDate.toString());
                }

                if (currDate.compareTo(compDate) <= 0) {
                    if (currTime + diff > upperTimeBound || compInt - currTime < diff) {
                        //case A: Push currDate to next Day, 8am
                        currTime = lowerTimeBound;
                        currDate = getAndSetDate(currDate, currTime / 100, 0, 0, 1);
                        //ui.showMessage("A1");
                    } else if (currTime + diff <= upperTimeBound && compInt - currTime >= diff) {
                        //case B: Slot currTime to be as it is
                        //ui.showMessage("B1");
                    } else if (compInt + diff <= upperTimeBound) {
                        //case C: Set currTime to be compTime
                        currTime = compInt;
                        currDate = getAndSetDate(currDate, currTime / 100, calendar.get(Calendar.MINUTE),
                                calendar.get(Calendar.SECOND), 0);
                        //ui.showMessage("C1");
                    } else {
                        //not supposed to be here
                        //ui.showMessage("DX1");
                    }
                } else {
                    //currDate after the compDate
                    if (currTime + diff > upperTimeBound) {
                        //case A: Push currDate to next Day, 8am
                        currTime = lowerTimeBound;
                        currDate = getAndSetDate(currDate, currTime / 100, 0, 0, 1);
                        //ui.showMessage("A2");
                    } else if (currTime + diff <= upperTimeBound) {
                        //case B: Slot currTime to be as it is
                        //ui.showMessage("B2");
                    } else {
                        //not supposed to be here
                        //ui.showMessage("CX2");
                    }
                }
            }
            ui.showMessage("Next Available: " + currDate.toString());
        } catch (NumberFormatException e) {
            throw new DukeException("Not a valid hour!");
        }
    }

    private Date getAndSetDate(Date date, int hour, int min, int sec, int dayIncrement) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + dayIncrement);
        date = cal.getTime();
        return date;
    }
}
