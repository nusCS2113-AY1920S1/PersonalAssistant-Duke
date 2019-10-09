package duke.command;

import duke.exceptions.ModEmptyCommandException;
import duke.exceptions.ModEmptyListException;
import duke.exceptions.ModInvalidTimeException;
import duke.modules.Deadline;
import duke.modules.Events;
import duke.modules.Task;

import duke.util.DateTimeParser;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import java.time.LocalDate;
import java.time.LocalTime;
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
        ArrayList<Task> printArray = new ArrayList<>();
        for (int i = 0; i < tasks.getSize(); i++) {
            if (tasks.access(i) instanceof Deadline) {
                Deadline d = (Deadline) tasks.access(i);
                if (currentDate.equals(d.getEnd().toLocalDate())) {
                    printArray.add(d);
                }
            } else if (tasks.access(i) instanceof Events) {
                Events e = (Events) tasks.access(i);
                if (currentDate.equals(e.getBegin().toLocalDate())) {
                    printArray.add(e);
                }
            }
        }
        printArray.sort(this::compare);
        boolean isEmpty = printArray.isEmpty();
        if (isEmpty) {
            throw new ModEmptyListException();
        } else {
            System.out.println("Here is your schedule for today:");
            ui.printTaskList(printArray);
        }
    }

    /**
     * Custom comparator function for sorting the schedule according to time.
     * @param t1 Task 1 to be compared
     * @param t2 Task 2 to be compared
     * @return true when Task t1 has an earlier time than Task t2
     */
    public int compare(Task t1, Task t2) {
        LocalTime time1 = t1.getTime().toLocalTime();
        LocalTime time2 = t2.getTime().toLocalTime();
        //ascending order
        return time1.compareTo(time2);
    }

    public boolean isExit() {
        return false;
    }
}

