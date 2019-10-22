package oof.command;

import oof.model.module.SemesterList;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.Todo;
import oof.Storage;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;

/**
 * Represents a command to select and recur a task.
 */
public class RecurringCommand extends Command {

    private int index;
    private int count;
    private int frequency;
    private static final int COUNT_MIN = 1;
    private static final int COUNT_MAX = 10;
    private static final int DAILY = 1;
    private static final int WEEKLY = 2;
    private static final int MONTHLY = 3;
    private static final int YEARLY = 4;
    private static final int DAYS_IN_WEEK = 7;

    /**
     * Constructor for RecurringCommand.
     * @param index Index of the task to be selected as a recurring task.
     * @param count Number of recurrences for the selected task.
     * @param frequency Frequency of recurrences for the selected task.
     */
    public RecurringCommand(int index, int count, int frequency) {
        super();
        this.index = index;
        this.count = count;
        this.frequency = frequency;
    }

    /**
     * Recurs a task.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input is invalid.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (!tasks.isIndexValid(this.index)) {
            throw new OofException("OOPS!!! Please select a valid task!");
        } else if (!isCountValid(this.count)) {
            throw new OofException("OOPS!!! The valid number of recurrences is from 1-10!");
        } else {
            try {
                if (!isFrequencyValid(this.frequency)) {
                    throw new OofException("OOPS!!! Please enter a valid number!");
                } else {
                    setRecurringTask(ui, tasks, this.index, this.count, this.frequency);
                    ui.printRecurringMessage(tasks);
                    storage.writeTaskList(tasks);
                }
            } catch (InputMismatchException e) {
                throw new OofException("OOPS!!! Please enter a valid number!");
            }
        }
    }

    /**
     * Sets the recurring task based on the number of recurrences and frequency.
     * @param ui Instance of Ui to display relevant messages for recurring tasks.
     * @param taskList Instance of TaskList that stores Task objects.
     * @param index Index of the task.
     * @param count Number of recurrences for the recurring task.
     * @param frequency Frequency of recurrence for the recurring task.
     */
    private void setRecurringTask(Ui ui, TaskList taskList,int index, int count, int frequency) throws OofException {
        Task task = taskList.getTask(index);
        task.setFrequency(frequency);
        taskList.deleteTask(index);
        taskList.addTaskToIndex(index, task);
        recurInstances(ui, taskList, task, count, frequency);
    }

    /**
     * Increments the datetime of a task based on recurring frequency and adds the task to TaskList.
     * @param ui Instance of Ui to display relevant messages for recurring tasks.
     * @param taskList Instance of TaskList that stores Task objects.
     * @param task Recurring task.
     * @param count Number of recurrences for the recurring task.
     * @param frequency Frequency of recurrence for the recurring task.
     */
    private void recurInstances(Ui ui, TaskList taskList, Task task, int count, int frequency) throws OofException {
        if (task instanceof Todo) {
            for (int i = 1; i <= count; i++) {
                addToDoTask(taskList, task, i, frequency);
            }
        } else if (task instanceof Deadline) {
            for (int i = 1; i <= count; i++) {
                addDeadlineTask(taskList, task, i, frequency);
            }
        } else if (task instanceof Event) {
            for (int i = 1; i <= count; i++) {
                addEventTask(taskList, task, i, frequency);
            }
        }
    }

    /**
     * Adds recurring todo task to the current list of task.
     * @param taskList Current list of tasks stored in program.
     * @param task Task to be set as a recurring instance.
     * @param index Represents the nth time to which the recurring instance has been generated.
     * @param frequency Represents the frequency of recurrence.
     * @throws OofException dateTimeIncrement method throws OofException.
     */
    private void addToDoTask(TaskList taskList, Task task, int index, int frequency) throws OofException {
        String date = ((Todo) task).getOn();
        date = dateTimeIncrement(date, frequency, index);
        Todo todo = new Todo(task.getDescription(), date);
        todo.setFrequency(frequency);
        taskList.addTask(todo);
    }

    /**
     * Adds recurring deadline task to the current list of task.
     * @param taskList Current list of tasks stored in program.
     * @param task Task to be set as a recurring instance.
     * @param index Represents the nth time to which the recurring instance has been generated.
     * @param frequency Represents the frequency of recurrence.
     * @throws OofException dateTimeIncrement method throws OofException.
     */
    private void addDeadlineTask(TaskList taskList, Task task, int index, int frequency) throws OofException {
        String date = ((Deadline) task).getBy();
        date = dateTimeIncrement(date, frequency, index);
        Deadline deadline = new Deadline(task.getDescription(), date);
        deadline.setFrequency(frequency);
        taskList.addTask(deadline);
    }

    /**
     * Adds recurring event task to the current list of task.
     * @param taskList Current list of tasks stored in program.
     * @param task Task to be set as a recurring instance.
     * @param index Represents the nth time to which the recurring instance has been generated.
     * @param frequency Represents the frequency of recurrence.
     * @throws OofException dateTimeIncrement method throws OofException.
     */
    private void addEventTask(TaskList taskList, Task task, int index, int frequency) throws OofException {
        String startTiming = ((Event) task).getStartTime();
        startTiming = dateTimeIncrement(startTiming, frequency, index);
        String endTiming = ((Event) task).getEndTime();
        endTiming = dateTimeIncrement(endTiming, frequency, index);
        Event event = new Event(task.getDescription(), startTiming, endTiming);
        event.setFrequency(frequency);
        taskList.addTask(event);
    }

    /**
     * Increments the datetime based on the frequency of recurrence.
     * @param dateTime Date and time.
     * @param frequency Frequency of recurrence of task.
     * @param increment Number of hops from the first recurrence.
     * @return New datetime after incrementation.
     * @throws OofException Throws an exception if datetime cannot be parsed.
     */
    private String dateTimeIncrement(String dateTime, int frequency, int increment) throws OofException {
        SimpleDateFormat format;
        if (dateTime.split(" ").length == 1) {
            format = new SimpleDateFormat("dd-MM-yyyy");
        } else {
            format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(dateTime));
            if (frequency == DAILY) {
                calendar.add(Calendar.DATE, DAILY * increment);
            } else if (frequency == WEEKLY) {
                calendar.add(Calendar.DATE, DAYS_IN_WEEK * increment);
            } else if (frequency == MONTHLY) {
                calendar.add(Calendar.MONTH, COUNT_MIN * increment);
            } else if (frequency == YEARLY) {
                calendar.add(Calendar.YEAR, COUNT_MIN * increment);
            }
            dateTime = format.format(calendar.getTime());
        } catch (ParseException e) {
            throw new OofException("OOPS!!! Datetime is in the wrong format!");
        }
        return dateTime;
    }

    /**
     * Checks if the number of recurrences for the task selected is valid.
     * @param count Number of recurrences in user input.
     * @return True if count is valid, false otherwise.
     */
    private boolean isCountValid(int count) {
        return ((count >= COUNT_MIN) && (count <= COUNT_MAX));
    }

    /**
     * Checks if the frequency of the recurrence is valid.
     * @param frequency Frequency of the recurrence.
     * @return True if the frequency is valid, false otherwise.
     */
    private boolean isFrequencyValid(int frequency) {
        return ((frequency >= DAILY) && (frequency <= YEARLY));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
