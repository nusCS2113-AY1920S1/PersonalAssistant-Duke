package Parser;
import Commands.*;
import DukeExceptions.DukeException;
import DukeExceptions.DukeInvalidCommandException;

/**
 * This class distinguishes the main command and calls for methods with respect to the main command.
 */
public class MainParser {
    public static Command parse(String fullCommand) throws Exception {
        String [] stringSplit = fullCommand.split(" ");
        String command = stringSplit[0];
        switch (command) {
            case "add/e":
            case "add/d":
                return new AddParse(fullCommand).parse();

            case "delete/e":
            case "delete/d":
                return new DeleteParse(fullCommand).parse();

            case "recur/e":
                return new RecurParse(fullCommand).parse();

            case "remind/set":
            case "remind/rm":
                return new RemindParse(fullCommand).parse();

            case "/show":
                return new WorkloadParse(fullCommand).parse();

            case "filter":
                return new FilterCommand(fullCommand);
            case "help":
                return new HelpCommand();

            case "list":
            case "done":
                return  null;

            case "find":
                return new FindFreeTimesParse(fullCommand).parse();
            case "show/previous":
                return new ShowPreviousCommand(fullCommand);
            case "retrieve/freetime":
                return new RetrieveFreeTimesParse(fullCommand).parse();
            case "retrieve/previous":
                return new RetrievePreviousCommand(fullCommand);
            case "Week":
                return new WeekCommand(fullCommand);
            case "bye":
                return new ByeCommand();

            default:
                throw new DukeInvalidCommandException("Invalid input. Please type help to see all commands");
        }
    }
}