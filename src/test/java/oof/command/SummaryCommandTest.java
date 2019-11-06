package oof.command;

import oof.Oof;
import oof.exception.ParserException;
import oof.exception.command.CommandException;
import oof.model.task.Event;
import oof.model.task.Deadline;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.model.tracker.Tracker;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author debbiextan

public class SummaryCommandTest {

    private Oof oof = new Oof();
    private TaskList taskList = oof.getTaskList();
    private static final int INDEX_DATE = 0;

    /**
     * Tests behavior of displaying tomorrow's schedule where there are no tasks.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    public void execute_tomorrowSummaryWithoutTasks_scheduleShown() throws CommandException, ParserException {
        oof.executeCommand("summary");
        TaskList scheduleTasks = new TaskList();

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String tomorrow = simpleDateFormat.format(date);

        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            String currDate = "";
            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                currDate = todo.getTodoDate();
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                currDate = deadline.getDeadlineDateTime();
            } else if (task instanceof Event) {
                Event event = (Event) task;
                currDate = event.getStartDateTime();
            }
            if (currDate.equals(tomorrow)) {
                scheduleTasks.addTask(task);
            }
        }
        assertEquals(scheduleTasks.getSize(), 0);

    }

    /**
     * Tests behavior of displaying tomorrow's schedule where there is a task.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    public void execute_tomorrowSummaryWithTask_scheduleShown() throws CommandException, ParserException {

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String tomorrow = simpleDateFormat.format(date);
        oof.executeCommand("todo test1 /on " + tomorrow);

        oof.executeCommand("summary");
        int schedule = 0;
        TaskList scheduledTasks = new TaskList();
        for (int i = 0; i < taskList.getSize(); i++) {
            Task task = taskList.getTask(i);
            String currDate = "";

            if (task instanceof Todo) {
                Todo todo = (Todo) task;
                currDate = todo.getTodoDate();
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                currDate = deadline.getDeadlineDateTime().split(" ")[INDEX_DATE];
            } else if (task instanceof Event) {
                Event event = (Event) task;
                currDate = event.getStartDateTime().split(" ")[INDEX_DATE];
            }

            if (currDate.equals(tomorrow)) {
                scheduledTasks.addTask(task);
                schedule++;
            }
        }
        assertEquals(schedule, scheduledTasks.getSize());
        int index = taskList.getSize() - 1;
        oof.executeCommand("delete " + index);
    }
}
