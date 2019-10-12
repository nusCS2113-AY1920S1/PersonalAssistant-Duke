package oof.command;

import oof.task.Deadline;
import oof.task.Event;
import oof.task.Task;
import oof.task.Todo;
import oof.Storage;
import oof.TaskList;
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
    private static final int WEEK = 7;

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
     * Marks the specific Task defined by the user as a recurring task and adds the specified number of recurrences.
     *
     * @param taskList Instance of TaskList that stores Task objects.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @param storage  Instance of Storage that enables the reading and writing of Task
     *                 objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws OofException {
        if (!taskList.isIndexValid(this.index)) {
            throw new OofException("OOPS!!! Please select a valid task!");
        } else if (!isCountValid(this.count)) {
            throw new OofException("OOPS!!! The valid number of recurrences is from 1-10!");
        } else {
            try {
                if (!isFrequencyValid(this.frequency)) {
                    throw new OofException("OOPS!!! Please enter a valid number!");
                } else {
                    setRecurringTask(ui, taskList, this.index, this.count, this.frequency);
                    ui.printRecurringMessage(taskList);
                    storage.writeToFile(taskList);
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
            ui.printTaskList(taskList);
            throw new OofException("OOPS!!! Selected task is a todo, it will be labelled instead!");
        } else if (task instanceof Deadline) {
            for (int i = 1; i <= count; i++) {
                String dateTime = ((Deadline) task).getBy();
                dateTime = dateTimeIncrement(task, dateTime, frequency, i);
                Task newTask = new Deadline(task.getLine(), dateTime);
                newTask.setFrequency(frequency);
                taskList.addTask(newTask);
            }
        } else if (task instanceof Event) {
            for (int i = 1; i <= count; i++) {
                String startTiming = ((Event) task).getStartTiming();
                String endTiming = ((Event) task).getEndTiming();
                startTiming = dateTimeIncrement(task, startTiming, frequency, i);
                endTiming = dateTimeIncrement(task, endTiming, frequency, i);
                Task newTask = new Event(task.getLine(), startTiming, endTiming);
                newTask.setFrequency(frequency);
                taskList.addTask(newTask);
            }
        }
    }

    /**
     * Increments the datetime based on the frequency of recurrence.
     * @param task Recurring task.
     * @param datetime Date and time.
     * @param frequency Frequency of recurrence of task.
     * @param increment Number of hops from the first recurrence.
     * @return New datetime after incrementation.
     * @throws OofException Throws an exception if datetime cannot be parsed.
     */
    private String dateTimeIncrement(Task task, String datetime, int frequency, int increment) throws OofException {
        try {
            SimpleDateFormat format;
            if (task instanceof Deadline) {
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            } else {
                format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(datetime));
            if (frequency == DAILY) {
                calendar.add(Calendar.DATE, DAILY * increment);
            } else if (frequency == WEEKLY) {
                calendar.add(Calendar.DATE, WEEK * increment);
            } else if (frequency == MONTHLY) {
                calendar.add(Calendar.MONTH, COUNT_MIN * increment);
            } else if (frequency == YEARLY) {
                calendar.add(Calendar.YEAR, COUNT_MIN * increment);
            }
            datetime = format.format(calendar.getTime());
        } catch (ParseException e) {
            throw new OofException("OOPS!!! Datetime is in the wrong format!");
        }
        return datetime;
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
