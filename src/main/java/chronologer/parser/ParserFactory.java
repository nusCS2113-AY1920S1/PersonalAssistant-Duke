package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.ExitCommand;
import chronologer.command.ExportCommand;
import chronologer.command.HelpCommand;
import chronologer.command.ListCommand;
import chronologer.command.ManualCommand;
import chronologer.command.RedoCommand;
import chronologer.command.StoreVersionCommand;
import chronologer.command.UndoCommand;
import chronologer.command.WeekCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiTemporary;

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
     * @throws ChronologerException The DukeException class has all the respective methods
     *                       and messages!
     */
    public static Command parse(String userInput) throws ChronologerException {
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
        case "assignment":
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
        case "schedule":
            return new ScheduleParser(userInput, command).parse();
        case "export":
            return new ExportParser(userInput,command).parse();
        case "undo":
            return new UndoCommand();
        case "redo":
            return new RedoCommand();
        case "theme":
            return new ThemeParser(userInput,command).parse();
        case "restore":
            return new RestoreVersionParser(userInput,command).parse();
        case "store":
            return new StoreVersionParser(userInput,command).parse();
        case "week":
            return new WeekParser(userInput,command).parse();
        case "help":
            return new HelpCommand();
        case "manual":
            return new ManualCommand();
        case "exam": //fallthrough
        case "examination":
            command = "exam";
            return new EventParser(userInput, command).parse();
        case "lecture": //fallthrough
        case "tutorial":
            return new RecurringEventParser(userInput, command).parse();
        default:
            // Empty string or unknown command.
            UiTemporary.printUnknownInput();
            UiTemporary.userOutputForUI = WRONG_COMMAND;
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }
    }
}