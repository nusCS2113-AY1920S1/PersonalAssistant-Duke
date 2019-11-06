package oof.command;

import oof.Oof;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import oof.exception.ParserException;
import oof.exception.command.CommandException;
import oof.model.task.Event;
import oof.model.task.Deadline;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import org.junit.jupiter.api.Test;

//@@author debbiextan

public class ScheduleCommandTest {

    private Oof oof = new Oof();
    private TaskList taskList = oof.getTaskList();

    /**
     * Tests behavior when no date is given.
     */
    @Test
    public void execute_dateEmpty_exceptionThrown() {
        try {
            oof.executeCommand("schedule");
            fail();
        } catch (CommandException | ParserException e) {
            assertEquals("OOPS! Please enter a date!", e.getMessage());
        }
    }

    /**
     * Tests behavior of displaying schedule by date when there are no tasks for that date.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    public void execute_correctDescriptionWithoutTasks_scheduleShown() throws CommandException, ParserException {
        String date = "01-01-2020";
        oof.executeCommand("schedule " + date);
        TaskList scheduleTasks = new TaskList();
        int schedule = 0;

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
            if (currDate.equals(date)) {
                scheduleTasks.addTask(task);
                schedule++;
            }
        }
        assertEquals(schedule, scheduleTasks.getSize());
    }

    /**
     * Tests behavior of displaying schedule by date when there are tasks on that date.
     *
     * @throws CommandException if command is invalid.
     * @throws ParserException  if command cannot be parsed.
     */
    @Test
    public void execute_correctDescriptionWithTasks_scheduleShown() throws CommandException, ParserException {
        String date = "02-01-2020";
        oof.executeCommand("todo test1 /on " + date);
        oof.executeCommand("schedule " + date);
        TaskList scheduleTasks = new TaskList();
        int schedule = 0;

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
            if (currDate.equals(date)) {
                scheduleTasks.addTask(task);
                schedule++;
            }
        }
        assertEquals(schedule, scheduleTasks.getSize());
        int last = taskList.getSize() - 1;
        oof.executeCommand("delete " + last);
    }
}
