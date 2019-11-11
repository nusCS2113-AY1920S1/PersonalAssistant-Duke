package chronologer.parser;

import chronologer.command.Command;
import chronologer.command.ExitCommand;
import chronologer.command.ListCommand;
import chronologer.command.UndoCommand;
import chronologer.command.RedoCommand;
import chronologer.command.HelpCommand;
import chronologer.command.ManualCommand;
import chronologer.command.TesterCommand;
import chronologer.command.ClearCommand;
import chronologer.exception.ChronologerException;

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

    private static final String TODO = "todo";
    private static final String DEADLINE = "deadline";
    private static final String ASSIGNMENT = "assignment";
    private static final String EVENT = "event";
    private static final String FIND = "find";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
    private static final String PRIORITY = "priority";
    private static final String DONE = "done";
    private static final String REMIND = "remind";
    private static final String POSTPONE = "postpone";
    private static final String VIEW = "view";
    private static final String LIST = "list";
    private static final String BYE = "bye";
    private static final String SEARCH = "search";
    private static final String IGNORE_INPUT = "ignore";
    private static final String UNIGNORE_INPUT = "unignore";
    private static final String COMMENT = "comment";
    private static final String LOCATION = "location";
    private static final String SCHEDULE = "schedule";
    private static final String EXPORT = "export";
    private static final String UNDO = "undo";
    private static final String REDO = "redo";
    private static final String THEME = "theme";
    private static final String RESTORE = "restore";
    private static final String STORE = "store";
    private static final String WEEK = "week";
    private static final String HELP = "help";
    private static final String MANUAL = "manual";
    private static final String EXAM = "exam";
    private static final String EXAMINATION = "examination";
    private static final String LECTURE = "lecture";
    private static final String TUTORIAL = "tutorial";

    private static final String TESTER = "tester";
    private static final String CLEAR = "sudo-clear";

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
        String command = userInput.split("\\s+", 2)[0].trim().toLowerCase();

        switch (command) {
        case TODO:
            if (userInput.contains(Flag.BETWEEN.getFlag())) {
                return new TodoWithinPeriodParser(userInput, command).parse();
            }
            if (userInput.contains(Flag.FOR.getFlag())) {
                return new TodoWithDurationParser(userInput, command).parse();
            }
            return new TodoParser(userInput, command).parse();
        case DEADLINE:
        case ASSIGNMENT:
            return new DeadlineParser(userInput, command).parse();
        case EVENT:
            return new EventParser(userInput, command).parse();
        case FIND:
            return new FindParser(userInput, command).parse();
        case EDIT:
            return new EditParser(userInput, command).parse();
        case DELETE:
            return new DeleteParser(userInput, command).parse();
        case PRIORITY:
            return new PriorityParser(userInput, command).parse();
        case DONE:
            return new DoneParser(userInput, command).parse();
        case REMIND:
            return new RemindParser(userInput, command).parse();
        case POSTPONE:
            return new PostponeParser(userInput, command).parse();
        case VIEW:
            return new ViewParser(userInput, command).parse();
        case LIST:
            return new ListCommand();
        case BYE:
            return new ExitCommand();
        case SEARCH:
            return new SearchParser(userInput, command).parse();
        case IGNORE_INPUT:
            return new IgnoreParser(userInput, command, IGNORE).parse();
        case UNIGNORE_INPUT:
            return new IgnoreParser(userInput, command, UNIGNORE).parse();
        case COMMENT:
            return new CommentParser(userInput, command).parse();
        case LOCATION:
            return new LocationParser(userInput, command).parse();
        case SCHEDULE:
            return new ScheduleParser(userInput, command).parse();
        case EXPORT:
            return new ExportParser(userInput,command).parse();
        case UNDO:
            return new UndoCommand();
        case REDO:
            return new RedoCommand();
        case THEME:
            return new ThemeParser(userInput,command).parse();
        case RESTORE:
            return new RestoreVersionParser(userInput,command).parse();
        case STORE:
            return new StoreVersionParser(userInput,command).parse();
        case WEEK:
            return new WeekParser(userInput,command).parse();
        case HELP:
            return new HelpCommand();
        case MANUAL:
            return new ManualCommand();
        case EXAM: //fallthrough
        case EXAMINATION:
            command = EXAM;
            return new EventParser(userInput, command).parse();
        case LECTURE: //fallthrough
        case TUTORIAL:
            return new RecurringEventParser(userInput, command).parse();
        case TESTER:
            return new TesterCommand();
        case CLEAR:
            return new ClearCommand();
        default:
            // Empty string or unknown command.
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }
    }
}