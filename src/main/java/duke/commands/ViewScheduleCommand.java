package duke.commands;

import duke.TaskList;
import duke.Storage;
import duke.Ui;
import duke.exceptions.BadInputException;
import duke.items.DateTime;
import duke.items.Task;
import duke.enums.CommandType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewScheduleCommand extends Command {

    private DateTime day;

    /**
     * Constructs a viewschedulecommand.
     * @param type Command type enum
     * @param day Date to check schedule in format of "DD/MM/YYYY"
     */
    public ViewScheduleCommand(CommandType type, String day) throws BadInputException {
        super(type);
        String[] tmpDay = day.split("/");

        this.day = new DateTime(tmpDay[0] + "/" + tmpDay[1] + "/" + tmpDay[2] + " 0000");
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        ArrayList<Task> scheduledTasks = new ArrayList<>();
        ArrayList<Task> tasks = list.getTaskList();

        Calendar dayDate = day.getAt();

        for (Task task : tasks) {
            Calendar currTaskDate;

            if ((currTaskDate = task.getDate()) != null) {
                if (currTaskDate.get(Calendar.DATE) == dayDate.get(Calendar.DATE)
                        && currTaskDate.get(Calendar.MONTH) == dayDate.get(Calendar.DAY_OF_MONTH)
                        && currTaskDate.get(Calendar.YEAR) == dayDate.get(Calendar.YEAR)) {
                    scheduledTasks.add(task);
                    System.out.println("Something added");
                }

            }
        }

        if (scheduledTasks.size() > 0) {
            System.out.println("Tasks scheduled for " + dayDate.get(Calendar.DATE) + "/"
                    + (dayDate.get(Calendar.MONTH) + 1) + "/"
                    + (dayDate.get(Calendar.YEAR) + 1900));

            for (int i = 0; i < scheduledTasks.size(); i++) {
                System.out.println(i + 1 + ". " + scheduledTasks.get(i).toString());
            }
        } else {
            System.out.println("Nothing is scheduled!");
        }

    }
}
