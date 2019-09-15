package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.tasks.Task;

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
            int currDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);;
            ui.showMessage("Current Date: " + currDate.toString());

            if (currTime + diff >= upperTimeBound) {
                calendar.set(Calendar.DAY_OF_MONTH, currDay + 1);
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                currDate = calendar.getTime();
            }

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

                //ui.showMessage("task: " + compDate.toString());

                calendar.setTime(compDate);
                int compInt = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);
                //ONLY HAPPENS if freetime AFTER lowerBound AND got freetime btw currTime and {time of task}
                if (currTime >= lowerTimeBound && compInt - diff >= currTime) {
                    //ui.showMessage("A");
                    break;
                } else if (compInt + diff <= upperTimeBound && currTime + diff <= upperTimeBound) {
                    //This happens if can find freetime AFTER event, and btw currTime and upperBound
                    //ui.showMessage("B");
                    calendar.set(Calendar.HOUR_OF_DAY, Math.max(compInt, currTime) / 100);
                    calendar.set(Calendar.MINUTE, Math.max(compInt, currTime) % 100);
                    currDate = calendar.getTime();
                    currTime = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE);;
                    //ui.showMessage("curr set to: " + currDate.toString());
                } else {
                    //This happens if can find freetime AFTER event, currTime and upperBound
                    //ui.showMessage("C");
                    currDay++;
                    currTime = 800;
                    calendar.set(Calendar.DAY_OF_MONTH, currDay);
                    calendar.set(Calendar.HOUR_OF_DAY, currTime / 100);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    currDate = calendar.getTime();
                }
            }
            ui.showMessage("Next Available: " + currDate.toString());
        } catch (NumberFormatException e) {
            throw new DukeException("Not a valid hour!");
        }
    }
}
