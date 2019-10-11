package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.model.tasks.RecurringTask;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static compal.commons.Messages.MESSAGE_MISSING_EDATE;
import static compal.commons.Messages.MESSAGE_MISSING_EDATE_ARG;
import static compal.commons.Messages.MESSAGE_INVALID_DATE_FORMATTING;
import static compal.commons.Messages.MESSAGE_INVALID_YEAR;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_INVALID_TIME_RANGE;

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
     * Returns a date string if specified in the task.
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

            String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(dateInput);

            if (matcher.matches() == false) {
                compal.ui.printg(MESSAGE_INVALID_DATE_FORMATTING);
                throw new Compal.DukeException(MESSAGE_INVALID_DATE_FORMATTING);
            }
            int inputSize = dateInput.length();

            String year = dateInput.substring(inputSize - 4, inputSize);
            int inputYear = Integer.parseInt(year);
            int currYear = Calendar.getInstance().get(Calendar.YEAR);

            if (inputYear < currYear) {
                compal.ui.printg(MESSAGE_INVALID_YEAR);
                throw new Compal.DukeException(MESSAGE_INVALID_YEAR);
            }
            return dateInput;
        } else {
            compal.ui.printg(MESSAGE_MISSING_EDATE_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_EDATE_ARG);
        }
    }

    /**
     * Increases date by week, to assign lesson slots for each week
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
     * Converts a date string to a Date object.
     *
     * @param dateStr The date string to be converted.
     * @return The date string in the form of a Date object.
     */
    public Date stringToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Converts a Date object to a date string. Correct type for creating a Task object.
     *
     * @param date The date in the form of a Date object.
     * @return The date in the form of a String object.
     */
    public String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    /**
     * Determines the type of recurring task - normal recurring task,
     * lecture, tutorial, sectional or lab, based on the first command
     * keyword entered by the user. Returns the type of symbol used for
     * that type of task.
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

    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        String userCmd = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String startDateStr = getDate(restOfInput);
            String startTime = getStartTime(restOfInput);
            String endTime = getEndTime(restOfInput);
            String endDateStr = getEndDate(restOfInput);
            String symbol = getSymbol(userCmd);

            if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                compal.ui.printg(MESSAGE_INVALID_TIME_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_TIME_RANGE);
            }

            Date initialDate = stringToDate(startDateStr);
            Date endDate = stringToDate(endDateStr);
            while (initialDate.before(endDate)) {
                String initialDateStr = dateToString(initialDate);
                RecurringTask newRecurTask = new RecurringTask(description, priority, initialDateStr,
                        startTime, endTime, symbol); // for testing tut command only
                taskList.addTask(newRecurTask);
                int arrSize = taskList.arrlist.size() - 1;
                String descToPrint = taskList.arrlist.get(arrSize).toString();
                compal.ui.printg(descToPrint);
                initialDate = incrementDateByWeek(initialDate);
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
    }
