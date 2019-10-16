package duke.parser;

import duke.Duke;
import duke.command.*;
import duke.command.AddCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.RemindCommand;
import duke.command.ViewCommand;
import duke.exception.DukeException;
import duke.orderCommand.*;
import duke.task.Deadline;
import duke.task.DoWithinPeriodTasks;
import duke.task.Event;
import duke.task.Todo;

import java.util.Date;

/**
 * Represents a parser used to parse the input String from the user into a Duke understandable {@link Command}.
 * It should deals with making sense of the user command.
 */
public class Parser {

    //There is no constructor method for all others are static.

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
                Date splittedDate = Convert.stringToDate(splitted[1]);
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

    public static OrderCommand parse(String fullCommand) throws DukeException {
        //splitted contains the keyword and the rest (description or task number)
        String[] splitted = fullCommand.split(" ", 2);
        //switching on the keyword
        switch (splitted[0]) {
            case "add":
                return new AddOrder();
            case "list":
                if (splitted[1].startsWith("undone")) { return new ListUndoneOrders(); }
                else return new ListAllOrders();
            case "done":
                checkLength(splitted);
                return new DoneOrder(splitted[1]);
            case "cancel":
                return new CancelOrder(splitted[1]);
            case "postpone":
                checkLength(splitted);
                String[] getUntil = splitAndCheck(splitted[1], " /by ");
                return new PostponeOrder(Integer.parseInt(getUntil[0]), getUntil[1]);
            case "find":
                return new FindOrderByDate(splitted[1]);
            default:
                throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
    /**
     * Checks the length of a String array is of size 2.
     * @throws DukeException when array is not of size 2.
     */
    public static void checkLength(String[] str) throws DukeException {
        if (str.length != 2) {
            throw new DukeException("The description cannot be empty.");
        }
    }

    /**
     * Split a string and check its length.
     */
    public static String[] splitAndCheck(String str, String regex) throws DukeException {
        String[] part = str.split(regex, 2);
        checkLength(part); //Throws DukeException
        return part;
    }

    /**
     * Converts a string into a number, and checks if it is out of bounds.
     * @return Returns a valid integer
     * @throws DukeException when it is invalid
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
}
