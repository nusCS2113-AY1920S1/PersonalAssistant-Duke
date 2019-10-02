package duke.parser;

import duke.Duke;
import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.command.RemindCommand;
import duke.command.Snooze;
import duke.command.ViewCommand;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.DoWithinPeriodTasks;
import duke.task.Event;
import duke.task.Todo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a parser used to parse the input String from the user into a Duke understandable {@link Command}.
 * It should deals with making sense of the user command.
 */
public class Parser {

    /**
     * The constructor method for Parser.
     */
    public Parser(int x){
        //do nothing, everything here is static
    }

    /**
     * Returns a {@link Command} that can be understood by {@link Duke} and executed after.
     * We first split the fullCommand into 2, the keyword, followed by everything else.
     * Then we perform switching based on the keyword.
     *
     * @param fullCommand The String command entered by the user
     * @param size The size of the TaskList
     * @return Command The Command to be executed
     * @throws DukeException for any invalid input
     */
    public static Command parse(String fullCommand, int size) throws DukeException {
        //splitted contains the keyword and the rest (description or task number)
        String[] splitted = fullCommand.split(" ", 2);
        //switching on the keyword
        switch (splitted[0]) {
            case "list":
                return new ListCommand();
            case "bye":
                return new ExitCommand();
            case "done":
                checkLength(splitted);
                return new DoneCommand(checkNumber(splitted[1], size));
            case "todo":
                checkLength(splitted);
                return new AddCommand(new Todo(splitted[1]));
            case "deadline":
                checkLength(splitted);
                String[] getBy = splitAndCheck(splitted[1], " /by ");
                return new AddCommand(new Deadline(getBy[0], getBy[1]));
            case "event":
                checkLength(splitted);
                String[] getAt = splitAndCheck(splitted[1], " /at ");
                return new AddCommand(new Event(getAt[0], getAt[1]));
            case "find":
                checkLength(splitted);
                return new FindCommand(splitted[1]);
            case "delete":
                checkLength(splitted);
                return new DeleteCommand(checkNumber(splitted[1], size));
            case "remind":
                return new RemindCommand();
            case "snooze":
                checkLength(splitted);
                String[] getUntil = splitAndCheck(splitted[1], " /until ");
                return new Snooze(checkNumber(getUntil[0], size), getUntil[1]);
            case "view":
                checkLength(splitted);
                Date splittedDate = stringToDate(splitted[1]);
                return new ViewCommand(splittedDate);
            case "period":
                checkLength(splitted);
                String[] getPart = splitAndCheck(splitted[1], " /from ");
                String[] part = splitAndCheck(getPart[1], " /to ");
                return new AddCommand(new DoWithinPeriodTasks(getPart[0], part[0], part[1]));
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Checks the length of a String array is of size 2.
     */
    public static void checkLength(String[] str) throws DukeException {
        if (str.length != 2) {
            throw new DukeException("The description cannot be empty.");
        }
    }

    /**
     * JAVADOC COMMENT.
     */
    public static String[] splitAndCheck(String str, String regex) throws DukeException {
        String[] part = str.split(regex, 2);
        checkLength(part); //Throws DukeException
        return part;
    }

    /**
     * Converts a string into a number, and checks if it is out of bounds.
     *
     * @return Returns a valid integer
     */
    public static int checkNumber(String str, int size) throws DukeException {
        int x;
        try {
            //Minus one because index starts from zero.
            //Throws NumberFormatException
            x = Integer.parseInt(str) - 1;
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        if (x < 0 || x >= size) {
            //Index is out of bounds
            throw new DukeException("FUCK YOU JOEY!");
        }
        return x;
    }

    /**
     * Returns the suffix to be used after the days in the Date, useful for printing the Date in the desired format.
     *
     * @param n indication the Day of the month
     * @return the suffix accordingly to the day of the month needed
     */
    public static String getDaySuffix(int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    /**
     * Returns a {@link Date} representation of a String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy".
     *
     * @param date String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy", used to extract a {@link Date} instance from
     * @return the {@link Date} instance created from the argument string
     */
    public static Date stringToDate(String date) {
        DateFormat formatter;
        if (date.length() > 11) {
            formatter = new SimpleDateFormat("dd/MM/yyyy hhmm");
        } else {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
        }
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Returns the {@link Date } instance as a String to be printed in the file.
     *
     * @param date deadline {@link Date} for finishing the task
     * @return String the date for the deadline
     */
    public static String getDateString(Date date, String dateString) {
        if (date == null) {
            return dateString;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern;
        if (dateString.length() > 11) {
            pattern = "d'" + Parser.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha ";
        } else {
            pattern = "d'" + Parser.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
