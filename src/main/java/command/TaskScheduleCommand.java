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
            d = (Deadline) list.get(indexOfTask);
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

        boolean isFreeBetweenEvents = false;
        for (int i = 0; i < dateList.size(); i++) {
            LocalDateTime nextStartDate;
            LocalDateTime currentEndDate = dateList.get(i).endDate;
            if (i == dateList.size() - 1) {
                nextStartDate = deadlineDate;
                if (currentEndDate.isAfter(deadlineDate)) {
                    currentEndDate = deadlineDate;
                }
            } else {
                nextStartDate = dateList.get(i+1).startDate;
            }


            Long duration;
            duration = ChronoUnit.HOURS.between(currentEndDate, nextStartDate);
            if (durationToSchedule <= duration) {
                isFreeBetweenEvents = true;
                Ui.printOutput("You can schedule this task from " + dateList.get(0).endDate
                        + " till " + deadlineDate);
            }
        }

        if (!isFreeBetweenEvents) {
            Ui.printOutput("There is no free slot to insert the task");
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
}

