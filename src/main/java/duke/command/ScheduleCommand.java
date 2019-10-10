package duke.command;

import duke.exceptions.ModEmptyCommandException;
import duke.exceptions.ModEmptyListException;
import duke.exceptions.ModInvalidTimeException;

import duke.modules.Task;
import duke.modules.TaskWithPeriod;
import duke.util.DateTimeParser;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ScheduleCommand extends Command {

    private String input;
    private LocalDate currentDate;

    /**
     * Constructor for the ScheduleCommand class that takes in the user input.
     * @param input User's input in the command line.
     * @throws ModEmptyCommandException If the user inputs and empty command.
     * @throws ModInvalidTimeException If the user does not input a date/command after "schedule ".
     */
    public ScheduleCommand(String input) throws ModInvalidTimeException, ModEmptyCommandException {
        this.input = input;
        if (input.length() <= 9) {
            throw new ModEmptyCommandException();
        }
        currentDate = DateTimeParser.getStringToDate(input.substring(9)).toLocalDate();

    }

    /**
     * This method finds all the tasks scheduled on the date that the user specifies, and adds them
     * to an ArrayList of Tasks if the dates match.
     * It then sorts the new ArrayList printArray according to the time the task is scheduled.
     * @param tasks TaskList object containing current active taskList.
     * @param ui Ui object containing all output methods to user.
     * @param storage Storage object for storing the taskList.
     * @throws ModEmptyListException When no tasks are found to match that date.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Reminder reminder) throws ModEmptyListException {
        ArrayList<Task> printArray = ScheduleCommand.getTasksIn(this.currentDate, tasks, true);
        boolean isEmpty = printArray.isEmpty();
        if (isEmpty) {
            throw new ModEmptyListException();
        } else {
            System.out.println("Here is your schedule for today:");
            ui.printTaskList(printArray);
        }
    }

    /**
     * Get all tasks in a specified date.
     * @param localDate input date
     * @param tasks task list to look up for
     * @param isSorted should the results be sorted
     * @return an array list containing all tasks in the input date
     */
    public static ArrayList<Task> getTasksIn(LocalDate localDate, TaskList tasks, boolean isSorted) {
        ArrayList<Task> ret = new ArrayList<>();
        for (int i = 0; i < tasks.getSize(); i++) {
            if (tasks.access(i) instanceof TaskWithPeriod) {
                TaskWithPeriod t = (TaskWithPeriod) tasks.access(i);
                if (localDate.equals(t.getTime().toLocalDate())) {
                    ret.add(t);
                }
            }
        }
        if (isSorted) {
            ret.sort(ScheduleCommand::compareTime);
        }
        return ret;
    }

    /**
     * Custom comparator function for sorting the schedule according to time.
     * @param t1 Task 1 to be compared
     * @param t2 Task 2 to be compared
     * @return true when Task t1 has an earlier time than Task t2
     */
    public static int compareTime(Task t1, Task t2) {
        LocalDateTime time1 = t1.getTime();
        LocalDateTime time2 = t2.getTime();
        //ascending order
        return (time1 == null) ? (time2 == null ? 0 : -1) : (time2 == null ? 1 : time1.compareTo(time2));
    }

    public boolean isExit() {
        return false;
    }
}
