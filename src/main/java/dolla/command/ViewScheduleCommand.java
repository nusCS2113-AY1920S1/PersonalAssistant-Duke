package dolla.command;

import dolla.Ui;
import dolla.task.Task;
import dolla.task.TaskList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import dolla.Time;

/**
 * The type View schedule command.
 */
public abstract class ViewScheduleCommand extends Command {

    protected LocalDate viewDate;
    protected String dateStr;

    /**
     * Instantiates a new View schedule command.
     *
     * @param dateStr the date string
     */
    public ViewScheduleCommand(String dateStr) {
        if (dateStr.equals("today")) {
            viewDate = LocalDate.now();
        } else {
            this.viewDate = Time.readDate(dateStr);
        }
        this.dateStr = dateStr;
    }

    /**
     * TODO: [placeholder].
     * @param tasks TODO: [placeholder]
     */
    //@Override
    public void execute(TaskList tasks) {
        ArrayList<String> msg = new ArrayList<String>();
        ArrayList<Task> tasksOnGivenDate = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.getFromList(i);
            if (currTask.getDate().toLocalDate().compareTo(viewDate) == 0) {
                tasksOnGivenDate.add(currTask);
            }
        }
        tasksOnGivenDate.sort(Comparator.comparing(Task::getDate));
        msg.add("Here is the schedule for " + dateStr + ":");
        for (int i = 0; i < tasksOnGivenDate.size(); i++) {
            msg.add((i + 1) + "."  + tasksOnGivenDate.get(i).getTask());
        }
        Ui.printMsg(msg);
    }
}
