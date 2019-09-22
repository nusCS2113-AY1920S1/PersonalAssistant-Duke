package compal.logic.commands;

import compal.logic.parser.CommandParser;
import compal.main.Duke;
import compal.tasks.Event;
import compal.tasks.FixedDurationTask;
import compal.tasks.TaskList;

import java.text.ParseException;
import java.util.Scanner;

public class FixedDurationCommand extends Command implements CommandParser {

    private final String TOKEN = "/on";
    private final String TOKEN_HOUR = "/hr";
    private final String TOKEN_MINUTE = "/min";
    private TaskList taskList;

    public FixedDurationCommand(Duke d) {
        super(d);
        this.taskList = d.tasklist;
    }

    /**
     * Adds a single ToDo to the tasklist and print out confirmation for the user.
     *
     * @param userIn Entire String input by the user.
     */
    @Override
    public void Command(String userIn) throws Duke.DukeException {
        Scanner scanner = new Scanner(userIn);
        String event = scanner.next();
        if (scanner.hasNext()) {
            String restOfInput = scanner.nextLine();
            String description = getDescription(restOfInput);
            String date = getDate(restOfInput);
            String time = getTime(restOfInput);
            int hour = getHour(restOfInput);
            int minute = getMinute(restOfInput);
            taskList.addTask(new FixedDurationTask(description, date,time, hour, minute));
            int arrSize = taskList.arrlist.size()-1;
            String descToPrint = taskList.arrlist.get(arrSize).toString();
            duke.ui.printg(descToPrint);

        } else {
            duke.ui.printg("InputError: Required input for FixedDuration command!");
            throw new Duke.DukeException("InputError: Required input for FixedDuration command!");
        }
    }

    public int getHour(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(TOKEN_HOUR)) {
            int startPoint = restOfInput.indexOf(TOKEN_HOUR);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if(!scanner.hasNext()){
                duke.ui.printg("HourInputError: Required hour input for FixedDuration command!");
                throw new Duke.DukeException("HourInputError: Required hour input for FixedDuration command!");
            }
            return scanner.nextInt();
        } else {
            duke.ui.printg("HourInputError: Required hour input for FixedDuration command!");
            throw new Duke.DukeException("HourInputError: Required hour input for FixedDuration command!");
        }
    }

    public int getMinute(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(TOKEN_MINUTE)) {
            int startPoint = restOfInput.indexOf(TOKEN_MINUTE);
            String dateStartInput = restOfInput.substring(startPoint);
            Scanner scanner = new Scanner(dateStartInput);
            scanner.next();
            if(!scanner.hasNext()){
                duke.ui.printg("MinInputError: Required minute input for FixedDuration command!");
                throw new Duke.DukeException("MinInputError: Required minute input for FixedDuration command!");
            }
            return scanner.nextInt();
        } else {
            duke.ui.printg("MinInputError: Required minute input for FixedDuration command!");
            throw new Duke.DukeException("MinInputError: Required minute input for FixedDuration command!");
        }
    }
}

