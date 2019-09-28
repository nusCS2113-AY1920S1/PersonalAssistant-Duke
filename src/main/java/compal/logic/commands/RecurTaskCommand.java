package compal.logic.commands;

import compal.compal.Compal;
import compal.logic.parser.CommandParser;
import compal.tasks.RecurringTask;
import compal.tasks.Task;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static compal.compal.Messages.*;

/**
 * Executes user command "recurtask".
 */
public class RecurTaskCommand extends Command implements CommandParser {
    // Todo: new keyword /freq, determines whether weekly, monthly, daily
    private static final String TOKEN_REP = "/rep";
    private static final String TOKEN_FREQ = "/freq";
    private static final int DEFAULT_FREQ_NUM = 7;
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
     * @throws Compal.DukeException
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
     *
     * @param restOfUserInput
     * @return Number of days between each recurring task.
     * Will default to DEFAULT_FREQ_NUM.
     */
    public static int getFreq(String restOfUserInput) {
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
     * @param freqNum The number of days between each task.
     * @return Final incremented date.
     */
    public static String incrementDate(String dateString, int freqNum) {
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
     * Adds multiple RecurringTask into taskList and prints confirmation message to user.
     *
     * @param userIn Entire user string input.
     * @throws Compal.DukeException If user input after "recurtask" is empty.
     */
    @Override
    public void parseCommand(String userIn) throws Compal.DukeException {
        Scanner scanner = new Scanner(userIn);
        String recurtask = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            Task.Priority priority = getPriority(restOfInput);
            String date = getDate(restOfInput);
            String time = getTime(restOfInput);
            int rep = getRep(restOfInput);
            int freq = getFreq(restOfInput);
            String dateStr = date;
            for (int count = 0; count < rep; count++) {
                RecurringTask newRecurTask = new RecurringTask(description, priority, dateStr, time);
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
