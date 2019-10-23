package command;

import exception.DukeException;
import storage.Storage;
import task.*;
import ui.Ui;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class TaskScheduleCommand extends Command {

    private Long durationToSchedule;
    private final int indexOfTask;
    private final int indexOfDeadline;

    public TaskScheduleCommand(int indexOfTask, int indexDeadline) {
        this.indexOfTask = indexOfTask;
        this.indexOfDeadline = indexDeadline;
    }
    
    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        ArrayList<Task> list = tasks.getTasks();
        TodoWithDuration t;
        Deadline d;
        try {
            t = (TodoWithDuration) list.get(indexOfTask);
        } catch (ClassCastException e) {
            throw new DukeException("Task selected is not a Todo with a duration");
        }
        try {
            d = (Deadline) list.get(indexOfDeadline);
        } catch (ClassCastException e) {
            throw new DukeException("Task selected is not a Deadline");
        }
        durationToSchedule = (long) t.duration;
        LocalDateTime deadlineDate = d.startDate;

        ArrayList<Event> dateList = createDateList(list, deadlineDate);
        if (dateList.size() == 0) {
            Ui.printOutput("You can schedule this task from now till the deadline.\n"
                    + "Schedule it at the earliest convenience?");
            return;
        }


        Long duration;
        LocalDateTime nextStartDate = dateList.get(0).startDate;
        duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            Ui.printOutput("You can schedule this task from now till " + nextStartDate);
            return;
        }


        boolean isFreeBetweenEvents = false;
        for (int i = 0; i < dateList.size(); i++) {
            LocalDateTime currentEndDate = dateList.get(i).endDate;
            if (i == dateList.size() - 1) {
                nextStartDate = deadlineDate;
                if (currentEndDate.isAfter(deadlineDate)) {
                    currentEndDate = deadlineDate;
                }
            } else {
                nextStartDate = dateList.get(i+1).startDate;
            }

            duration = ChronoUnit.HOURS.between(currentEndDate, nextStartDate);
            if (durationToSchedule <= duration) {
                isFreeBetweenEvents = true;
                Ui.printOutput("You can schedule this task from " + currentEndDate
                        + " till " + nextStartDate);
                    break;
            }
        }

        if (!isFreeBetweenEvents) {
            Ui.printOutput("There is no free slot to insert the task. Consider freeing up your schedule.");
        }
    }

    private ArrayList<Event> createDateList(ArrayList<Task> tasks, LocalDateTime deadlineDate) {
        ArrayList<Event> dateList = new ArrayList<>();
        for (Task item : tasks) {
            if (item.getClass() == task.Event.class) {
                if (item.startDate.isBefore(deadlineDate))
                    dateList.add((Event) item);
            }
        }
        Collections.sort(dateList);

        return dateList;
    }

    //TODO: Figure a way for GUI to accept subsequent inputs
    private boolean confirmSchedule(Task t, LocalDateTime start, long duration, TaskList tasks, Storage storage)
            throws DukeException {
        while(true) {
            String answer = Ui.readInput().toLowerCase();
            if (answer.equals("y")) {
                String description = t.description + "(Recommended period)";
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