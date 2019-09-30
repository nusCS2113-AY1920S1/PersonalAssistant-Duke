package compal.logic.commands;

import compal.commons.Compal;
import compal.logic.parser.CommandParser;
import compal.tasks.RecurringTask;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static compal.commons.Messages.MESSAGE_INVALID_TIME_RANGE;
import static compal.commons.Messages.MESSAGE_MISSING_COMMAND_ARG;
import static compal.commons.Messages.MESSAGE_MISSING_REP;
import static compal.commons.Messages.MESSAGE_MISSING_REP_ARG;

/**
 * Executes user command for recurring tasks, lectures, tutorials,
 * sectionals and labs.
 */
public class RecurTaskCommand extends Command implements CommandParser {
    private static final String TOKEN_REP = "/rep";
    private static final String TOKEN_FREQ = "/freq";
    private static final int DEFAULT_FREQ_NUM = 7;
    private static final String CMD_LECT = "lect";
    private static final String CMD_TUT = "tut";
    private static final String CMD_SECT = "sect";
    private static final String CMD_LAB = "lab";
    private static final String SYMBOL_LECT = "LECT";
    private static final String SYMBOL_TUT = "TUT";
    private static final String SYMBOL_SECT = "SECT";
    private static final String SYMBOL_LAB = "LAB";
    private static final String SYMBOL_DEFAULT = "RT";
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
     * @throws Compal.DukeException If rep field is empty or rep token
     *                              (/rep) is missing.
     */
    public int getRep(String restOfUserInput) throws Compal.DukeException {
        if (restOfUserInput.contains(TOKEN_REP)) {
            int splitPoint = restOfUserInput.indexOf(TOKEN_REP);
            String repPart = restOfUserInput.substring(splitPoint);
            Scanner scanner = new Scanner(repPart);
            scanner.next();
            if (!scanner.hasNext()) {
                compal.ui.printg(MESSAGE_MISSING_REP);
                throw new Compal.DukeException(MESSAGE_MISSING_REP);
            }
            int repNum = scanner.nextInt();
            return repNum;
        } else {
            compal.ui.printg(MESSAGE_MISSING_REP_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_REP_ARG);
        }
    }

    /**
     * Returns the number of days between the recurring task in an integer form.
     * Will default to DEFAULT_FREQ_NUM.
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
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, freqNum);
        return format.format(calendar.getTime());
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
            return SYMBOL_DEFAULT;
        }
    }

    /**
     * Adds multiple RecurringTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user string input.
     * @throws Compal.DukeException If user input after "recurtask" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        String userCmd = scanner.next();
        String symbol = getSymbol(userCmd);
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            String startTime = getStartTime(restOfInput);
            String endTime = getEndTime(restOfInput);

            if (Integer.parseInt(startTime) > Integer.parseInt(endTime)) {
                compal.ui.printg(MESSAGE_INVALID_TIME_RANGE);
                throw new Compal.DukeException(MESSAGE_INVALID_TIME_RANGE);
            }

            int rep = getRep(restOfInput);
            int freq = getFreq(restOfInput);
            String dateStr = date;
            for (int count = 0; count < rep; count++) {
                RecurringTask newRecurTask = new RecurringTask(description, priority, dateStr,
                        startTime, endTime, symbol);
                taskList.addTask(newRecurTask);
                int arrSize = taskList.arrlist.size() - 1;
                String descToPrint = taskList.arrlist.get(arrSize).toString();
                compal.ui.printg(descToPrint);
                dateStr = incrementDate(dateStr, freq);
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
