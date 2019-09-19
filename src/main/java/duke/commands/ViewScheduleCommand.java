package duke.commands;

import duke.TaskList;
import duke.Storage;
import duke.Ui;
import duke.exceptions.BadInputException;
import duke.items.DateTime;
import duke.items.Task;

import java.util.ArrayList;
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

        Date dayDate = day.getAt();

        for (Task task : tasks) {
            Date currTaskDate;

            if ((currTaskDate = task.getDate()) != null) {
                if (currTaskDate.getDate() == dayDate.getDate()
                        && currTaskDate.getMonth() == dayDate.getMonth()
                        && currTaskDate.getYear() == dayDate.getYear()) {

                    scheduledTasks.add(task);
                    System.out.println("Something added");

                }
            }
        }


        if (scheduledTasks.size() > 0) {
            System.out.println("Tasks scheduled for " + dayDate.getDate() + "/"
                    + (dayDate.getMonth() + 1) + "/"
                    + (dayDate.getYear() + 1900));

            for (int i = 0; i < scheduledTasks.size(); i++) {
                System.out.println(i + ". " + scheduledTasks.get(i).toString());
            }
        } else {
            System.out.println("Nothing is scheduled!");
        }

    }
}
