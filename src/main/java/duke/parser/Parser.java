package duke.parser;

import duke.Duke;
import duke.command.*;
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
 * Represents a parser used to parse the input String from the user into a Duke understandable {@link Command}
 * It should deals with making sense of the user command
 */
public class Parser {

    /**
     * Returns a {@link Command} that can be understood by {@link Duke} and executed after
     * @param fullCommand the String command entered by the user
     * @return Command the Command to be executed
     * @throws DukeException
     */
    public static Command parse(String fullCommand) throws DukeException {
        String[] splitted = fullCommand.split(" ", 2);// splitted contains the keyword and the rest (description or task number)
        switch (splitted[0]) { // switching on the keyword
            case "list":
                if (splitted.length == 2) {
                    throw new DukeException("Did you mean just list?");
                } else {
                    return new ListCommand();
                }
            case "bye":
                if (splitted.length == 2) {
                    throw new DukeException("Did you mean just bye?");
                } else {
                    return new ExitCommand();
                }
            case "done":
                if (splitted.length == 2) {
                    int taskNb = Integer.parseInt(splitted[1]);
                    return new DoneCommand(taskNb - 1);
                } else {
                    throw new DukeException("Need a task number after done!");
                }
            case "todo":
                if ((splitted.length == 1) || splitted[1].isEmpty()){
                    throw new DukeException("The description of a todo cannot be empty.");
                }
                return new AddCommand(new Todo(splitted[1]));
            case "deadline":
                if ((splitted.length == 1) || splitted[1].isEmpty()){
                    throw new DukeException("The description of a deadline cannot be empty.");
                }
                String[] getBy = splitted[1].split("/by ", 2);
                if (getBy.length < 2){
                    throw new DukeException("The description of a deadline must contain /by date!");
                }
                return new AddCommand(new Deadline(getBy[0], getBy[1]));
            case "event":
                if ((splitted.length == 1) || splitted[1].isEmpty()){
                    throw new DukeException("The description of an event cannot be empty, and it must contain /at");
                }
                String[] getAt = splitted[1].split("/at ", 2);
                if (getAt.length < 2){
                    throw new DukeException("The description of a deadline must contain /at data and time from-to!");
                }
                return new AddCommand(new Event(getAt[0], getAt[1]));
            case "find":
                if (splitted.length == 2) {
                    return new FindCommand(splitted[1]);
                } else {
                    throw new DukeException("Need a word to find! ");
                }
            case "delete":
                if (splitted.length == 2) {
                    int taskNb = Integer.parseInt(splitted[1]);
                    return new DeleteCommand(taskNb - 1);
                } else {
                    throw new DukeException("Need a task number after done!");
                }
            case "snooze":
                if ((splitted.length == 1) || splitted[1].isBlank()){
                    throw new DukeException("The description of a snooze cannot be empty.");
                }
                String[] getUntil = splitted[1].split("/until ", 2);
                if (getUntil.length < 2){
                    throw new DukeException("The description of a snooze must contain /until date!");
                }
            case "view":
                if ((splitted.length == 1) || splitted[1].isBlank()) {
                    throw new DukeException("The description of a view must contain date!");
                }
                else{
                    Date splittedDate = Parser.getDate(splitted[1]);
                    return new ViewCommand(splittedDate);
                }
            case "period":
                if ((splitted.length == 1) || splitted[1].isEmpty()){
                    throw new DukeException("The description of a period cannot be empty.");
                }
                String[] getPart = splitted[1].split("/from ", 2);
                if (getPart.length < 2){
                    throw new DukeException("The description of a period must contain /from date!");
                }
                String[] part = getPart[1].split("/to ", 2);
                if (part.length < 2){
                    throw new DukeException("NO");
                }
                try {
                    return new AddCommand(new DoWithinPeriodTasks(getPart[0], part[0], part[1]));
                }
                catch(Exception e){
                    throw new DukeException("NO");
                }
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Returns the suffix to be used after the days in the Date, usefull for printing the Date in the desired format
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
     * Returns a {@link Date} instance representation of a String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy"
     * @param date String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy", used to extract a {@link Date} instance from
     * @return the {@link Date} instance created from the argument string
     */
    public static Date getDate(String date) {
        DateFormat dateFormat = (date.length() > 11) ? new SimpleDateFormat("dd/MM/yyyy hhmm") : new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateFormat.parse(date);
            return dateFormat.parse(date);
        } catch (ParseException e) {
            //case the date was not valid!
        }
        return null;
    }
    /**
     * Returns the {@link Date } instance as a String to be printed in the file
     * @param date deadline {@link Date} for finishing the task
     * @return String the date for the deadline
     */
    public static String getDateString(Date date, String dateString) {
        if (date == null)
            return dateString;
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern = dateString.length() > 11 ? "d'" + Parser.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha " : "d'" + Parser.getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
