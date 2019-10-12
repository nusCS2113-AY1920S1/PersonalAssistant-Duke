package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.RecurringTask;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_DATE_TIME_INPUT;
import static compal.commons.Messages.MESSAGE_INVALID_REP_RANGE;
import static compal.commons.Messages.MESSAGE_INVALID_TIME_RANGE;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_EDATE;
import static compal.commons.Messages.MESSAGE_MISSING_REP;
import static compal.commons.Messages.MESSAGE_MISSING_REP_EDATE_ARG;

/**
 * Executes user command for recurring tasks, lectures, tutorials,
 * sectionals and labs.
 */
public class RecurTaskCommand extends Command implements CommandParser {
    private static final String TOKEN_REP = "/rep";
    private static final String TOKEN_FREQ = "/freq";
    private static final String TOKEN_END_DATE = "/edate";
    private static final int DEFAULT_FREQ_NUM = 7;
    private static final int MIN_REP_NUM = 2;
    private TaskList taskList;

    /**
     * Constructs RecurTaskCommand object.
     *
     * @param d Compal.
     */
    public RecurTaskCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Returns the number of repetitions of the recurring task in an integer form.
     *
     * @param restOfUserInput User input string.
     * @return Number of repetitions of the recurring task.
     * @throws Compal.DukeException If rep field is empty or rep given is less than the minimum of 2.
     */
    public int getRep(String restOfUserInput) throws Compal.DukeException {
        int splitPoint = restOfUserInput.indexOf(TOKEN_REP);
        String repPart = restOfUserInput.substring(splitPoint);
        Scanner scanner = new Scanner(repPart);
        scanner.next();
        if (!scanner.hasNext()) {
            compal.ui.printg(MESSAGE_MISSING_REP);
            throw new Compal.DukeException(MESSAGE_MISSING_REP);
        }
        int repNum = scanner.nextInt();
        if (repNum < MIN_REP_NUM) {
            compal.ui.printg(MESSAGE_INVALID_REP_RANGE);
            throw new Compal.DukeException(MESSAGE_INVALID_REP_RANGE);
        }
        return repNum;
    }

    /**
     * Returns an end date string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Date in the form of a string.
     * @throws Compal.DukeException If date field is empty, date or date format is invalid
     */
    public String getEndDate(String restOfInput) throws Compal.DukeException {
        int startPoint = restOfInput.indexOf(TOKEN_END_DATE);
        String dateStartInput = restOfInput.substring(startPoint);
        Scanner scanner = new Scanner(dateStartInput);
        scanner.next();
        if (!scanner.hasNext()) {
            compal.ui.printg(MESSAGE_MISSING_EDATE);
            throw new Compal.DukeException(MESSAGE_MISSING_EDATE);
        }
        String dateInput = scanner.next();
        String validatedDate = inputDateValidation(dateInput);
        return validatedDate;
    }

    /**
     * Returns the number of days between the recurring task in an integer form.
     * Will default to DEFAULT_FREQ_NUM.
     *
     * @param restOfUserInput User input string.
     * @return Number of days between each recurring task.
     */
    public int getFreq(String restOfUserInput) {
        int freqNum = DEFAULT_FREQ_NUM;
        if (restOfUserInput.contains(TOKEN_FREQ)) {
            int splitPoint = restOfUserInput.indexOf(TOKEN_FREQ);
            String freqPart = restOfUserInput.substring(splitPoint);
            Scanner scanner = new Scanner(freqPart);
            scanner.next();
            if (!scanner.hasNext()) {
                return freqNum;
            } else {
                freqNum = scanner.nextInt();
                return freqNum;
            }
        } else {
            return freqNum;
        }
    }

    /**
     * Increases the date by the number of days specified by the user.
     *
     * @param dateString The date to increment.
     * @param freqNum    The number of days between each task.
     * @return Final incremented date.
     */
    public String incrementDate(String dateString, int freqNum) {
        Date date = stringToDate(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, freqNum);
        Date newDate = calendar.getTime();
        String newDateString = dateToString(newDate);
        return newDateString;
    }

    /**
     * Add a single Recurring Task to the tasklist and prints out the output that
     * confirms the addition of that task.
     *
     * @param description Description of recurring task.
     * @param dateStr     Starting date of recurring task.
     * @param startTime   Starting time of recurring task.
     * @param priority    priority level of task type
     * @param endTime     End time of deadline
     */
    public void addRecurringTask(String description, Task.Priority priority, String dateStr,
                                 String startTime, String endTime) throws ParseException, Compal.DukeException {
        RecurringTask recurringTask = new RecurringTask(description, priority, dateStr,
                startTime, endTime);
        taskList.addTask(recurringTask);
        int arrSize = taskList.arrlist.size() - 1;
        String descToPrint = taskList.arrlist.get(arrSize).toString();
        compal.ui.printg(descToPrint);
    }

    /**
     * Adds multiple RecurringTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user string input.
     * @throws Compal.DukeException If user input after "recurtask" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        String recurTaskCmd = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            String startTime = getStartTime(restOfInput);
            String endTime = getEndTime(restOfInput);
            int freq = getFreq(restOfInput);

            if (!isValidDateAndTime(date, startTime)) {
                compal.ui.printg(MESSAGE_INVALID_DATE_TIME_INPUT);
                throw new Compal.DukeException(MESSAGE_INVALID_DATE_TIME_INPUT);
            }

            if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                compal.ui.printg(MESSAGE_INVALID_TIME_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_TIME_RANGE);
            }

            boolean hasEdate = restOfInput.contains(TOKEN_END_DATE);
            boolean hasRep = restOfInput.contains(TOKEN_REP);

            String dateStrForIncrement = date;
            if (hasEdate) {
                Date dateForEachTask = stringToDate(dateStrForIncrement);
                String endDateStr = getEndDate(restOfInput);
                Date endDate = stringToDate(endDateStr);
                while (dateForEachTask.before(endDate)) {
                    addRecurringTask(description, priority, dateStrForIncrement, startTime, endTime);
                    dateStrForIncrement = incrementDate(dateStrForIncrement, freq);
                    dateForEachTask = stringToDate(dateStrForIncrement);
                }
            } else if (hasRep) {
                int rep = getRep(restOfInput);
                for (int dateCount = 0; dateCount < rep; dateCount++) {
                    addRecurringTask(description, priority, dateStrForIncrement, startTime, endTime);
                    dateStrForIncrement = incrementDate(dateStrForIncrement, freq);
                }
            } else {
                compal.ui.printg(MESSAGE_MISSING_REP_EDATE_ARG);
                throw new Compal.DukeException(MESSAGE_MISSING_REP_EDATE_ARG);
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
