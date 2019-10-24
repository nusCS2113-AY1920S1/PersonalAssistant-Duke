package Parser;
import Commands.Command;

public class MainParser {
    public static Command parse(String fullCommand) {
        String [] stringSplit = fullCommand.split(" ");
        String command = stringSplit[0];
        switch (command) {
            case "add/e":
                break;
            case "add/d":
                break;
            case "delete/e":
                break;
            case "delete/d":
                break;
            case "recur/e":
                break;
            case "remind/set":
                break;
            case "remind/rm":
                break;
            case "/show":
                break;
            case "filter":
                break;
            case "help":
                break;
            case "list":
                break;
            case "done":
                break;
            case "Available":
                break;
            case "bye":
                break;
        }
    }
}
