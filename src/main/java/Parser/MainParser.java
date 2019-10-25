package Parser;
import Commands.ByeCommand;
import Commands.Command;
import Commands.FilterCommand;
import Commands.HelpCommand;

public class MainParser {
    public static Command parse(String fullCommand) throws Exception {
        String [] stringSplit = fullCommand.split(" ");
        String command = stringSplit[0];
        switch (command) {
            case "add/e":
            case "add/d":
                return new AddParse(fullCommand).execute();
                break;
            case "delete/e":
            case "delete/d":
                return new DeleteParse(fullCommand).execute();
                break;
            case "recur/e":
                return new RecurParse(fullCommand).execute();
                break;
            case "remind/set":
                break;
            case "remind/rm":
                break;
            case "/show":
                return new WorkloadParse(fullCommand).execute();
                break;
            case "filter":
                return new FilterCommand(fullCommand);
                break;
            case "help":
                return new HelpCommand();
                break;
            case "list":
                break;
            case "done":
                break;
            case "Find":
                return new FindFreeTimesParse(fullCommand).execute();
                break;
            case "bye":
                return new ByeCommand();
                break;
        }
    }
}
