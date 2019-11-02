package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//@@author Catherinetan99
public class FindFreeSlotCommand extends Command {

    public static final String MESSAGE_USAGE = "findfreeslot\n\t"
            + "Format: findfreeslot /date <dd/mm/yyyy> /hour <num> /min <num>\n\n\t"
            + "Note: content in \"<>\": need to be fulfilled by the user\n\t"
            + "You can switch the order of any two blocks (a block starts with \"/\" and ends by the next block)\n\t"
            + "dd/mm/yyyy is the date format. e.g. 01/01/2000\n\n"
            + "This command will show all free time slots on that day with <num> hours <num> minutes\n"
            + "Examples:\n\t"
            + "findfreeslot /date 01/01/2019 /hour 1 /min 10\n\t\t"
            + "find all free time slots which is longer than 1h10min on 01/01/2019";
    public static final String MESSAGE_LIMIT_EXCEEDED = "Error: Input entered is out of range!";
    private static final long MAX_DURATION = 86400000;

    private Date date;
    private int hour;
    private int min;

    /**
     * Constructs FindFreeSlotCommand object.
     *
     * @param date input date
     * @param hour input hour
     * @param min input minute
     */
    public FindFreeSlotCommand(Date date, int hour, int min) {
        this.date = date;
        this.hour = hour;
        this.min = min;
    }

    /**
     * Returns a list of free time slots available on the input date with the input hour and min.
     *
     * @param taskList List of all tasks
     * @return list of free time slots
     * @throws CommandException if command cannot be executed
     */
    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {

        Date startPointer;
        Date oneDayAfter;

        Calendar calendar = Calendar.getInstance();
        Date currentDateAndTime = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.DATE, 1);
        oneDayAfter = calendar1.getTime();

        ArrayList<Task> arrayList = new ArrayList<>();
        TaskList resultTaskList = new TaskList();
        resultTaskList.setArrList(arrayList);

        for (Task task : taskList.getArrList()) {
            if (!task.getStringEndTime().equals("-") && !task.getStringStartTime().equals("-")
                    && task.getEndTime().after(currentDateAndTime)) {
                if (task.getMainDate().equals(date)
                        && (task.getStringTrailingDate().equals("-") || task.getTrailingDate().equals(date))) {
                    resultTaskList.addTask(task);
                } else if (task.getMainDate().equals(date) && !task.getStringTrailingDate().equals("-")) {
                    Task addedTask = task;
                    addedTask.setEndTime("2359");
                    resultTaskList.addTask(addedTask);
                } else if (!task.getStringTrailingDate().equals("-") && task.getTrailingDate().equals(date)) {
                    Task addedTask = task;
                    addedTask.setStartTime("0000");
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    addedTask.setMainDate(dateFormat.format(date));
                    resultTaskList.addTask(addedTask);
                }
            }
        }

        resultTaskList.sortTask(arrayList);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date currentDate = calendar.getTime();

        if (isEqualDate(date, currentDate)) {
            if (arrayList.isEmpty()) {
                startPointer = currentDateAndTime;
            } else if (arrayList.get(0).getEndTime().after(currentDateAndTime)) {
                startPointer = arrayList.get(0).getEndTime();
            } else {
                startPointer = currentDateAndTime;
            }
        } else {
            startPointer = date;
        }

        ArrayList<String> finalList;
        long duration = calculateDuration(hour, min);
        if ((duration > MAX_DURATION) || (hour > 24) || (min > 1440)) {
            throw new CommandException(MESSAGE_LIMIT_EXCEEDED);
        }

        finalList = getFreeSlots(arrayList, startPointer, oneDayAfter, duration);
        String result = printResult(finalList);

        arrayList.clear();
        finalList.clear();

        return new CommandResult(result, false);
    }

    /**
     * Returns a String array of free time slots with input duration.
     *
     * @param arrayList list of tasks on input date
     * @param startTime start time
     * @param endTime date of next day
     * @param duration duration of time slot needed
     * @return
     */
    public ArrayList<String> getFreeSlots(ArrayList<Task> arrayList, Date startTime, Date endTime, long duration) {
        Date startPointer = startTime;
        Date endPointer;
        ArrayList<String> stringArrayList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmm");

        for (int i = 0; i < arrayList.size(); i++) {
            endPointer = arrayList.get(i).getStartTime();

            if ((endPointer.getTime() - startPointer.getTime()) >= duration) {
                String start = simpleDateFormat.format(startPointer);
                String end = simpleDateFormat.format(endPointer);
                stringArrayList.add(start + " to " + end + "\n");
            }
            if (startPointer.getTime() < arrayList.get(i).getEndTime().getTime()) {
                startPointer = arrayList.get(i).getEndTime();
            }
        }

        if ((endTime.getTime() - startPointer.getTime()) >= duration) {
            String start = simpleDateFormat.format(startPointer);
            stringArrayList.add(start + " to 2400\n");
        }

        return stringArrayList;
    }

    /**
     * Returns a String to be printed as the result.
     *
     * @param arrayList list of available time slots
     * @return result String
     */
    public String printResult(ArrayList<String> arrayList) {
        StringBuilder finalList = new StringBuilder();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = simpleDateFormat.format(date);

        if (arrayList.isEmpty()) {
            return ("You have no available slots on " + stringDate + " ! :(");
        }

        finalList.append("Here are the available time slots for " + stringDate + ":\n");
        for (int i = 1; i <= arrayList.size(); i++) {
            finalList.append(i + ". " + arrayList.get(i - 1));
        }

        return finalList.toString();
    }

    /**
     * Returns true if the dates are equal, returns false otherwise.
     *
     * @param date1 First date input
     * @param date2 Second date input
     * @return true of dates are equal, false otherwise
     */
    public boolean isEqualDate(Date date1, Date date2) {
        return date1.equals(date2);
    }

    /**
     * Gets duration in milliseconds.
     *
     * @param hour Hour input
     * @param min Minute input
     * @return Duration in milliseconds
     */
    public long calculateDuration(int hour, int min) {
        return (hour * 60 + min) * 60000;
    }
}
