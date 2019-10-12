package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.AcadTask;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_DATE_TIME_INPUT;
import static compal.commons.Messages.MESSAGE_INVALID_TIME_RANGE;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_DATE;
import static compal.commons.Messages.MESSAGE_MISSING_DATE_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_EDATE;
import static compal.commons.Messages.MESSAGE_MISSING_EDATE_ARG;

public class AcadCommand extends Command implements CommandParser {
    private static final String CMD_LECT = "lect";
    private static final String CMD_TUT = "tut";
    private static final String CMD_SECT = "sect";
    private static final String CMD_LAB = "lab";
    private static final String SYMBOL_LECT = "LECT";
    private static final String SYMBOL_TUT = "TUT";
    private static final String SYMBOL_SECT = "SECT";
    private static final String SYMBOL_LAB = "LAB";
    private static final String SYMBOL_ACAD = "ACAD";
    private static final String TOKEN_END_DATE = "/edate";
    private static final char TOKEN_SLASH_CHAR = '/';
    private static final int DEFAULT_WEEK_INTERVAL = 7;
    private TaskList taskList;

    /**
     * Constructs RecurTaskCommand object.
     *
     * @param d Compal.
     */
    public AcadCommand(Compal d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Accepts a number of start dates for Academic Tasks that may have several sessions in one week.
     * Returns a list of the start dates for the sessions in the first week.
     *
     * @param restOfInput Input description after initial command word.
     * @return An ArrayList of date strings.
     * @throws Compal.DukeException If date field is empty, date or date format is invalid,
     *                              date token (/date) is missing.
     */
    public ArrayList<String> getStartDateList(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TOKEN_DATE)) {
            int startPoint = restOfInput.indexOf(TOKEN_DATE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_DATE);
                throw new Compal.DukeException(MESSAGE_MISSING_DATE);
            }
            int dateCount = 0;
            ArrayList<String> startDateList = new ArrayList<String>();
            while (scanner.hasNext() && dateCount < DEFAULT_WEEK_INTERVAL) {
                String eachDateInput = scanner.next();
                if (eachDateInput.charAt(0) == TOKEN_SLASH_CHAR) {
                    break;
                }
                String validatedDate = inputDateValidation(eachDateInput);
                startDateList.add(validatedDate);
                dateCount++;
            }
            return startDateList;
        } else {
            compal.ui.printg(MESSAGE_MISSING_DATE_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_DATE_ARG);
        }
    }

    /**
     * Returns an end date string if specified in the task.
     *
     * @param restOfInput Input description after initial command word.
     * @return Date in the form of a string.
     * @throws Compal.DukeException If date field is empty, date or date format is invalid,
     *                              end date token (/edate) is missing.
     */
    public String getEndDate(String restOfInput) throws Compal.DukeException {
        if (restOfInput.contains(TOKEN_END_DATE)) {
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
        } else {
            compal.ui.printg(MESSAGE_MISSING_EDATE_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_EDATE_ARG);
        }
    }

    /**
     * Increases date by week, to assign lesson slots for each week.
     *
     * @param initialDate The date to increment.
     * @return Final date one week later than initialDate.
     */
    public Date incrementDateByWeek(Date initialDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.DATE, DEFAULT_WEEK_INTERVAL);
        Date finalDate = calendar.getTime();
        return finalDate;
    }

    /**
     * Determines the type of academic task - lecture, tutorial, sectional or lab,
     * based on the first command keyword entered by the user.
     * Returns the type of symbol used for that type of task.
     *
     * @param userCmd The first command keyword entered by the user.
     * @return The symbol for that type of task.
     */
    public String getSymbol(String userCmd) {
        switch (userCmd) {
        case CMD_LECT:
            return SYMBOL_LECT;
        case CMD_TUT:
            return SYMBOL_TUT;
        case CMD_SECT:
            return SYMBOL_SECT;
        case CMD_LAB:
            return SYMBOL_LAB;
        default:
            return SYMBOL_ACAD;
        }
    }

    /**
     * Add a single Academic Task to the tasklist and prints out the output that
     * confirms the addition of that task.
     *
     * @param description Description of recurring task.
     * @param dateStr     Starting date of recurring task.
     * @param startTime   Starting time of recurring task.
     * @param priority    priority level of task type
     * @param endTime     End time of deadline
     */
    public void addAcadTask(String description, Task.Priority priority, String dateStr,
                            String startTime, String endTime, String symbol)
            throws ParseException, Compal.DukeException {
        AcadTask newAcadTask = new AcadTask(description, priority, dateStr,
                startTime, endTime, symbol);
        taskList.addTask(newAcadTask);
        int arrSize = taskList.arrlist.size() - 1;
        String descToPrint = taskList.arrlist.get(arrSize).toString();
        compal.ui.printg(descToPrint);
    }

    /**
     * Adds multiple AcadTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user string input.
     * @throws Compal.DukeException If user input after command word is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException, ParseException {
        Scanner scanner = new Scanner(userIn);
        String userCmd = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            ArrayList<String> startDateList = getStartDateList(restOfInput);
            String startTime = getStartTime(restOfInput);
            String endTime = getEndTime(restOfInput);
            String endDateStr = getEndDate(restOfInput);
            String symbol = getSymbol(userCmd);

            if (!isValidDateAndTime(startDateList.get(0), startTime)) {
                compal.ui.printg(MESSAGE_INVALID_DATE_TIME_INPUT);
                throw new Compal.DukeException(MESSAGE_INVALID_DATE_TIME_INPUT);
            }

            if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                compal.ui.printg(MESSAGE_INVALID_TIME_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_TIME_RANGE);
            }

            for (String initialDateStr : startDateList) {
                Date dateForEachTask = stringToDate(initialDateStr);
                Date endDate = stringToDate(endDateStr);
                while (dateForEachTask.before(endDate)) {
                    String dateStrForEachTask = dateToString(dateForEachTask);
                    addAcadTask(description, priority, dateStrForEachTask, startTime, endTime, symbol);
                    dateForEachTask = incrementDateByWeek(dateForEachTask);
                }
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
