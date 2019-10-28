package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Todo;
import chronologer.ui.UiTemporary;

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
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        ArrayList<Task> list = tasks.getTasks();
        Todo t;
        Deadline d;
        try {
            t = (Todo) list.get(indexOfTask);
        } catch (ClassCastException e) {
            UiTemporary.printOutput("Task selected is not a Todo with a duration");
            throw new ChronologerException("Task selected is not a Todo with a duration");
        }
        try {
            d = (Deadline) list.get(indexOfDeadline);
        } catch (ClassCastException e) {
            UiTemporary.printOutput("Task selected is not a Deadline");
            throw new ChronologerException("Task selected is not a Deadline");
        }
        durationToSchedule = (long) t.duration;
        LocalDateTime deadlineDate = d.getStartDate();

        ArrayList<Event> dateList = createDateList(list, deadlineDate);
        if (dateList.size() == 0) {
            UiTemporary.printOutput("You can schedule this task from now till the deadline.\n"
                    + "Schedule it at the earliest convenience?");
            return;
        }

        Long duration;
        LocalDateTime nextStartDate = dateList.get(0).getStartDate();
        duration = ChronoUnit.HOURS.between(LocalDateTime.now(), nextStartDate);
        if (durationToSchedule <= duration) {
            UiTemporary.printOutput("You can schedule this task from now till " + nextStartDate);
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
                UiTemporary.printOutput("You can schedule this task from " + currentEndDate
                        + " till " + nextStartDate);
                break;
            }
        }

        if (!isFreeBetweenEvents) {
            UiTemporary.printOutput("There is no free slot to insert the task. Consider freeing up your schedule.");
        }
    }

    private ArrayList<Event> createDateList(ArrayList<Task> tasks, LocalDateTime deadlineDate) {
        ArrayList<Event> dateList = new ArrayList<>();
        for (Task item : tasks) {
            //NOTE CHANGE HERE NO LONGER TASK.EVENT.CLASS
            if (item.getClass() == Event.class) {
                if (item.getStartDate().isBefore(deadlineDate)) {
                    dateList.add((Event) item);
                }
            }
        }
        Collections.sort(dateList);

        return dateList;
    }

    // TODO: Figure a way for GUI to accept subsequent inputs
    private boolean confirmSchedule(Task t, LocalDateTime start, long duration, TaskList tasks, Storage storage)
            throws ChronologerException {
        while (true) {
            String answer = UiTemporary.readInput().toLowerCase();
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
            UiTemporary.printOutput("Not a valid input. Please answer as y/n\n");
        }
    }
}