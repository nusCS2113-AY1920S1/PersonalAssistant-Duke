package parser;

import command.AddCommand;
import command.Command;
import command.CommentCommand;
import command.DeleteCommand;
import command.DoneCommand;
import command.ExitCommand;
import command.FindCommand;
import command.ListCommand;
import command.LocationCommand;
import command.PostponeCommand;
import command.PriorityCommand;
import command.RemindCommand;
import command.SearchCommand;
import command.ViewCommand;
import command.EditCommand;
import command.IgnoreCommand;
import exception.DukeException;
import task.Deadline;
import ui.Ui;

import java.time.LocalDateTime;
import java.text.ParseException;
import java.util.Date;

/**
 * The parser class is used to parse and make sense of the different queries the
 * user inputs into the program and tag them for further processing.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */


public class ParserFactory {
    private static final boolean IGNORE = true;
    private static final boolean UNIGNORE = false;
    private static final String WRONG_COMMAND = "Wrong Command";

    /**
     * Parses the user input of string type and returns the respective command type.
     *
     * @param userInput This string is provided by the user to ask 'Duke' to perform
     *                  a particular action
     * @return Command After processing the user's input it returns the correct
     *                  command for further processing
     * @throws DukeException The DukeException class has all the respective methods
     *                       and messages!
     */
    public static Command parse(String userInput) throws DukeException {
        String command = userInput.split("\\s+", 2)[0].trim();

        switch (command.toLowerCase()) {
        case "todo":
            if (userInput.contains(Flag.BETWEEN.getFlag())) {
                return new TodoWithinPeriodParser(userInput, command).parse();
            }
            if (userInput.contains(Flag.FOR.getFlag())) {
                return new TodoWithDurationParser(userInput, command).parse();
            }
            return new TodoParser(userInput, command).parse();
        case "deadline":
            return new DeadlineParser(userInput, command).parse();
        case "event":
            return new EventParser(userInput, command).parse();
        case "find":
            return new FindParser(userInput, command).parse();
        case "edit":
            return new EditParser(userInput, command).parse();
        case "delete":
            return new DeleteParser(userInput, command).parse();
        case "priority":
            return new PriorityParser(userInput, command).parse();
        case "done":
            return new DoneParser(userInput, command).parse();
        case "remind":
            return new RemindParser(userInput, command).parse();
        case "postpone":
            return new PostponeParser(userInput, command).parse();
        case "view":
            return new ViewParser(userInput, command).parse();
        case "list":
            return new ListCommand();
        case "bye":
            return new ExitCommand();
        case "search":
            return new SearchParser(userInput, command).parse();
        case "ignore":
            return new IgnoreParser(userInput, command, IGNORE).parse();
        case "unignore":
            return new IgnoreParser(userInput, command, UNIGNORE).parse();
        case "comment":
            return new CommentParser(userInput, command).parse();
        case "location":
            return new LocationParser(userInput, command).parse();

        default:
            // Empty string or unknown command.
            Ui.printUnknownInput();
            Ui.userOutputForUI = WRONG_COMMAND;
            throw new DukeException(DukeException.unknownUserCommand());
        }
    }
}