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
        duke.ui.printg(userIn);
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
            String statusIcon = taskList.arrlist.get(arrSize).getStatusIcon();
            duke.ui.printg("[FDT][" + statusIcon + "] " + description);
        } else {
            throw new Duke.DukeException(sadFace + " OOPS!!! The description of a " + event + " cannot be empty.");
        }
    }

    public int getHour(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(TOKEN_HOUR)) {
            Scanner scanner = new Scanner(restOfInput);
            scanner.next();
            int hour_input = scanner.nextInt();
            return hour_input;
        } else {
            throw new Duke.DukeException("Hour field cannot be empty. Please enter a valid date.");
        }
    }

    public int getMinute(String restOfInput) throws Duke.DukeException {
        if (restOfInput.contains(TOKEN_MINUTE)) {
            Scanner scanner = new Scanner(restOfInput);
            scanner.next();
            int minute_input = scanner.nextInt();
            return minute_input;
        } else {
            throw new Duke.DukeException("Minute field cannot be empty. Please enter a valid date.");
        }
    }
}

