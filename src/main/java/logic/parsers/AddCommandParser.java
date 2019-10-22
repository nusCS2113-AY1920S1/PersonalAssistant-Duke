package logic.parsers;

import logic.commands.AddCommand;
import logic.commands.Command;
import utils.DukeException;

import model.tasks.ToDo;
import model.tasks.Event;
import model.tasks.Deadline;
import model.tasks.Last;
import model.tasks.Period;
import model.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddCommandParser {
    public static Command parse(String userInput) throws DukeException {
        return new AddCommand(userInput);
    }

    /**
     * Adds the task to the database.
     *
     * @param line is the full command
     * @return the corresponding Task.Task object
     * @throws DukeException if the format of command cannot be parsed
     */
    public static Task pparse(String line) throws DukeException {
        String[] splites = line.trim().split("\\s+", 2);
        splites[0] = splites[0].toUpperCase();
        Task temp = null;
        if (splites.length < 2) {
            throw new DukeException("No descriptions");
        }
        if (splites[0].equals("TODO")) {
            temp = new ToDo(splites[1]);
            return temp;
        } else if (splites[0].equals("DEADLINE")) {
            splites = splites[1].split("/by");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /by");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp = new Deadline(splites[0], ft.parse(splites[1]));
                return temp;
            } catch (ParseException e) {
                throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy hhmm");
            }
        } else if (splites[0].equals("EVENT")) {
            splites = splites[1].split("/at");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /at");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                temp = new Event(splites[0], ft.parse(splites[1]));
                return temp;
            } catch (ParseException e) {
                throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy hhmm");
            }
        } else if (splites[0].equals("LAST")) {
            splites = splites[1].split("/last");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /last");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            temp = new Last(splites[0], splites[1]);
            return temp;
        } else if (splites[0].equals("PERIOD")) {
            splites = splites[1].split("/from");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /from");
            }
            String description = splites[0].trim();
            splites[1] = splites[1].trim();
            splites = splites[1].split("/to");
            if (splites.length < 2) {
                throw new DukeException("No time keyword /to");
            }
            splites[0] = splites[0].trim();
            splites[1] = splites[1].trim();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
            try {
                Date start = ft.parse(splites[0]);
                Date end = ft.parse(splites[1]);
                if (start.compareTo(end) > 0) {
                    throw new DukeException("Invalid time period, start time should before end time");
                }
                temp = new Period(description, start, end);
                return temp;
            } catch (ParseException e) {
                throw new DukeException("Invalid date format, the correct format is: dd/MM/yyyy hhmm");
            }
        } else {
            throw new DukeException("Task.Task type " + splites[0] + " not recognized");
        }
    }
}
