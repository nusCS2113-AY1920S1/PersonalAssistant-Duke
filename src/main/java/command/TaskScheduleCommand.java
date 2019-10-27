package command;

import exception.DukeException;
import storage.Storage;
import task.*;
import ui.Ui;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Finds a free period of time within the user's schedule for a selected duration value.
 *
 * @author Fauzan Adipratama
 * @version 1.3
 */
public class TaskScheduleCommand extends Command {

    private Long durationToSchedule;
    private final int indexOfTask;
    private final int indexOfDeadline;

    /**
     * Initialises the command parameter for a selected task to be done by a selected deadline.
     * @param indexOfTask is the index number of the selected task in the TaskList
     * @param indexDeadline is the index number of the selected deadline in the TaskList
     */
    public TaskScheduleCommand(int indexOfTask, int indexDeadline) {
        this.indexOfTask = indexOfTask;
        this.indexOfDeadline = indexDeadline;
    }

    /**
     * Searches all free periods of time that the user can schedule a given task by a certain deadline.
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @throws DukeException if the selected task is not a compatible type.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        ArrayList<Task> list = tasks.getTasks();
        Todo t;
        Deadline d;
        try {
            t = (Todo) list.get(indexOfTask);
        } catch (ClassCastException e) {
            throw new DukeException("Task selected is not a Todo with a duration");
        }
        try {
            d = (Deadline) list.get(indexOfDeadline);
        } catch (ClassCastException e) {
            throw new DukeException("Task selected is not a Deadline");
        }
        durationToSchedule = (long) t.duration;
        LocalDateTime deadlineDate = d.getStartDate();

        ArrayList<Event> dateList = tasks.obtainEventList(deadlineDate);
        if (dateList.size() == 0) {
            Ui.printOutput("You can schedule this task from now till the deadline.\n"
                    + "Schedule it at the earliest convenience?");
            return;
        }

        Long duration;
        LocalDateTime nextStartDate = dateList.get(0).getStartDate();
        duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            Ui.printOutput("You can schedule this task from now till " + nextStartDate);
            return;
        }

        boolean isFreeBetweenEvents = false;
        for (int i = 0; i < dateList.size(); i++) {
            LocalDateTime currentEndDate = dateList.get(i).getEndDate();
            if (i == dateList.size() - 1) {
                nextStartDate = deadlineDate;
                if (currentEndDate.isAfter(deadlineDate)) {
                    currentEndDate = deadlineDate;
                }
            } else {
                nextStartDate = dateList.get(i + 1).getStartDate();
            }

            duration = ChronoUnit.HOURS.between(currentEndDate, nextStartDate);
            if (durationToSchedule <= duration) {
                isFreeBetweenEvents = true;
                Ui.printOutput("You can schedule this task from " + currentEndDate + " till " + nextStartDate);
                break;
            }
        }

        if (!isFreeBetweenEvents) {
            Ui.printOutput("There is no free slot to insert the task. Consider freeing up your schedule.");
        }
    }

    // TODO: Figure a way for GUI to accept subsequent inputs
    private boolean confirmSchedule(Task t, LocalDateTime start, long duration, TaskList tasks, Storage storage)
            throws DukeException {
        while (true) {
            String answer = Ui.readInput().toLowerCase();
            if (answer.equals("y")) {
                String description = t.getDescription() + "(Recommended period)";
                LocalDateTime end = start.plusHours(duration);
                Command command = new AddCommand("todo", description, start, end);
                command.execute(tasks, storage);
                return true;
            }
            if (answer.equals("n")) {
                return false;
            }
            Ui.printOutput("Not a valid input. Please answer as y/n\n");
        }
    }
}