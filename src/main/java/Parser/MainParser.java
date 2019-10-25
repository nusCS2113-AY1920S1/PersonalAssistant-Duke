package Parser;
import Commands.ByeCommand;
import Commands.Command;
import Commands.FilterCommand;
import Commands.HelpCommand;
import DukeExceptions.DukeException;

public class MainParser {
    public static Command parse(String fullCommand) throws Exception {
        String [] stringSplit = fullCommand.split(" ");
        String command = stringSplit[0];
        switch (command) {
            case "add/e":
            case "add/d":
                return new AddParse(fullCommand).execute();

            case "delete/e":
            case "delete/d":
                return new DeleteParse(fullCommand).execute();

            case "recur/e":
                return new RecurParse(fullCommand).execute();

            case "remind/set":
            case "remind/rm":
                return new RemindParse(fullCommand).execute();

            case "/show":
                return new WorkloadParse(fullCommand).execute();

            case "filter":
                return new FilterCommand(fullCommand);

            case "help":
                return new HelpCommand();

            case "list":
            case "done":
                return  null;

            case "Find":
                return new FindFreeTimesParse(fullCommand).execute();

            case "bye":
                return new ByeCommand();

            default:
                throw new DukeException("Invalid input");
        }
    }
}
