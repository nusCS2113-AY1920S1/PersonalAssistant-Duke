/*
package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.task.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static duke.common.Messages.COMMAND_VIEWSCHEDULE;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.ERROR_MESSAGE_VIEWSCHEDULE;

public class ViewScheduleCommand extends Command {

    public ViewScheduleCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        if (userInputCommand.trim().equals(COMMAND_VIEWSCHEDULE)) {
            throw new DukeException(ERROR_MESSAGE_GENERAL + ERROR_MESSAGE_VIEWSCHEDULE);
        }
        String inputDate = userInputCommand.substring(13).trim();
        Date currDate = new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(currDate);

        int currDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currMonth = calendar.get(Calendar.MONTH);
        int currYear = calendar.get(Calendar.YEAR);

        ArrayList<Task> scheduleList = new ArrayList<>();
        for (Task task : taskList.getTaskList()) {
            if (task.getTaskType() == Task.TaskType.DEADLINE) {
                calendar.setTime(task.getDateTime());
                int taskDay = calendar.get(Calendar.DAY_OF_MONTH);
                int taskMonth = calendar.get(Calendar.MONTH);
                int taskYear = calendar.get(Calendar.YEAR);
                if (taskYear == currYear && taskMonth == currMonth && taskDay == currDay) {
                    scheduleList.add(task);
                }
            }
        }

        String outputDate = new SimpleDateFormat("dd MMMM yyyy").format(currDate);
        if (scheduleList.isEmpty()) {
            System.out.println("      No tasks on " + outputDate);
        } else {
            System.out.println("      Here are your tasks for " + outputDate);
            for (int i = 0; i < scheduleList.size(); i++) {
                System.out.println("      " + (i + 1) + ". " + scheduleList.get(i));
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}*/
