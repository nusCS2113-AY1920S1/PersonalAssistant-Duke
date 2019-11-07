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

            case "done/e":
            case "done/d":
                return new DoneParse(fullCommand).parse();

            case "recur/weekly":
            case "recur/biweekly":
            case "recur/rmweekly":
            case "recur/rmbiweekly":
                return new RecurParse(fullCommand).parse();

            case "remind/check":
            case "remind/set":
            case "remind/rm":
                return new RemindParse(fullCommand).parse();

            case "show/workload":
                return new WorkloadParse(fullCommand).parse();

            case "show/filter":
                return new FilterParse(fullCommand).parse();
            case "help":
                return new HelpCommand();

//            case "list":
            case "done":
                return new DoneParse(fullCommand).parse();

            case "find/ft":
                return new FindFreeTimesParse(fullCommand).parse();

            case "show/previous":
                return new ShowPreviousParse(fullCommand).parse();
            
            case "retrieve/ft":
                return new RetrieveFreeTimesParse(fullCommand).parse();

            case "retrieve/previous":
                return new RetrievePreviousCommand(fullCommand);

            case "show/week":
                return new WeekParse(fullCommand).parse();

            case "bye":
                return new ByeCommand();

            default:
                throw new DukeInvalidCommandException("Invalid input. Please type help to see all commands");
        }
    }
}
