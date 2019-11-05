package ducats;

import ducats.commands.*;
import ducats.components.Jaccard;
import ducats.components.WordGetter;
import ducats.Ui;


/**
 * A class used to interpret the incoming messages and translate them into the appropriate duke.Commands.
 */
public class Parser {

    /**
     * Returns the duke.Commands.duke.Commands.Command object interpreted from the input message,
     * and throws a duke.DukeException otherwise.
     *
     * @param message the input message to be parsed
     * @return the duke.Commands.duke.Commands.Command object interpreted from the input message
     * @throws DucatsException in the case of parsing errors
     */
    public static Command parse(Ui ui,String message) throws DucatsException {
        String [] commandList = {"bye", "list", "delete", "deletebar","editbar",
                                    "find","done", "new","help","view","addbar",
                                    "overlay","group","overlay_bar_group", "metronome",
                                    "overlay_group_group", "overlay_bar_song", "ascii",
                                    "redo", "undo", "open",
                                    "copy", "insertbar", "swapbar"};
        double maximumVal = 0;
        //String commandName = "";
        Jaccard similarityChecker = new Jaccard();
        String [] messageSplit = message.split(" ");
        WordGetter wordSimilarity = new WordGetter();
        String commandName = wordSimilarity.closestWord(messageSplit[0]);
        if (!commandName.equals(messageSplit[0])) {
            ui.autoCorrectMessage(commandName);
        }
        messageSplit[0] = commandName;
        message = String.join(" ", messageSplit);
        switch (commandName) {
        case "bye":
            if (message.length() == 3) {
                return new ByeCommand();
            }
            break;
        case "list":
            if (message.length() == 4) {
                return new ListCommand();
            }
            break;
        case "open":
            if (message.length() >= 6) {
                return new OpenCommand(message);
            }
            break;
        case "delete":
            if (message.length() >= 8) {
                return new DeleteCommand(message);
            }
            break;
        case "deletebar":
            if (message.length() >= 11) {
                return new DeleteBarCommand(message);
            }
            break;
        case "insertbar":
            if (message.length() >= 11) {
                return new InsertBarCommand(message);
            }
            break;
        case "swapbar":
            if (message.length() >= 9) {
                return new SwapBarCommand(message);
            }
            break;
        case "editbar":
            if (message.length() >= 9) {
                return new EditBarCommand(message);
            }
            break;
        case "new":
            if (message.length() >= 5) {
                return new NewCommand(message);
            }
            break;
        case "help": {
            return new HelpCommand(message);
        }
        case "view":
            if (message.length() >= 6) {
                return new ViewCommand(message);
            }
            break;
        case "addbar":
            if (message.length() >= 7) {
                return new AddBarCommand(message);
            }
            break;
        case "overlay":
            if (message.length() >= 8) {
                return new AddOverlayCommand(message);
            }
            break;
        case "group":
            if (message.length() >= 7) {
                return new GroupCommand(message);
            }
            break;
        case "overlay_bar_group":
            if (message.length() >= 8) {
                return new OverlayBarGroup(message);
            }
            break;
        case "overlay_group_group":
            if (message.length() >= 8) {
                return new OverlayGroupGroup(message);
            }
            break;
        case "overlay_bar_song":
            if (message.length() >= 8) {
                return new OverlayBarSong(message);
            }
            break;
        case "copy":
            if (message.length() >= 6) {
                return new CopyCommand(message);
            }
            break;
        case "ascii":
            if (message.length() >= 7) {
                return new AsciiCommand(message);
            }
            break;
        case "redo":
            if (message.length() == 4) {
                return new RedoCommand();
            }
            break;
        case "undo":
            if (message.length() == 4) {
                return new UndoCommand();
            }
            break;
        case "metronome":
            if (message.length() >= 11) {
                return new MetronomeCommand(message);
            }
            break;
        case "list_group":
            if(message.length() == 10){
                return new ListGroupCommand();
            }
            break;
        default:
            return new AddBarCommand(message);
        }
        throw new DucatsException(message);
    }
}
