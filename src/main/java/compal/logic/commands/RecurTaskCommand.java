package compal.logic.commands;

import compal.compal.Compal;
import compal.logic.parser.CommandParser;
import compal.tasks.RecurringTask;
import compal.tasks.Task;
import compal.tasks.TaskList;

import static compal.compal.Messages.MESSAGE_MISSING_COMMAND_ARG;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Executes user command "recurtask".
 */
public class RecurTaskCommand extends Command implements CommandParser {

    private static final String TOKEN_REP = "/rep";
    private static final String TOKEN = "/by";
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
     * Used in recurTaskPacker.
     *
     * @param restOfUserInput User input string.
     * @return Number of repetitions of the recurring task.
     */
    public static int getRep(String restOfUserInput) {
        int splitPoint = restOfUserInput.indexOf(TOKEN_REP);
        String repPart = restOfUserInput.substring(splitPoint);
        Scanner sc = new Scanner(repPart);
        sc.next();
        int repNum = sc.nextInt();
        return repNum;
    }

    /**
     * Increases the date by one week.
     * Used in recurTaskPacker.
     *
     * @param dateString The date to increment.
     * @return Final incremented date.
     */
    public static String incrementDateByWeek(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
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
            String dateStr = date;
            for (int count = 0; count < rep; count++) {
                RecurringTask newRecurTask = new RecurringTask(description, priority, dateStr, time);
                taskList.addTask(newRecurTask);
                int arrSize = taskList.arrlist.size() - 1;
                String descToPrint = taskList.arrlist.get(arrSize).toString();
                compal.ui.printg(descToPrint);
                dateStr = incrementDateByWeek(dateStr);
            }
        } else {
            compal.ui.printg(MESSAGE_MISSING_COMMAND_ARG);
            throw new Compal.DukeException(MESSAGE_MISSING_COMMAND_ARG);
        }
    }
}
