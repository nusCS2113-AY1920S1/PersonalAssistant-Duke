package duke.command;

import duke.exception.DukeException;
import duke.ui.Context;

import static java.lang.Math.min;

/**
 * Class for parsing user input from the UI in order to construct Commands of the appropriate type and parameters,
 * and execute them. The static initializer generates a static map from Cmd enum values to allow fast lookup of
 * command types.
 */
public class Parser {

    private final Commands commands;
    private final Context context;
    private final ArgParser argParser;

    /**
     * Constructs a new Parser, generating a HashMap from an array of enum values to allow fast lookup of command types.
     */
    public Parser(Context context, Commands commands) {
        this.commands = commands;
        this.context = context;
        this.argParser = new ArgParser();
    }

    /**
     * Constructs a new Parser, using the Cmd enum to supply the command names.
     */
    public Parser(Context context) {
        this(context, new Commands());
    }

    /**
     * Creates a new Command of the type requested by the user, and extracts the necessary data for the command from
     * the arguments. Literal line separators in the text will be converted to \n for consistency.
     *
     * @param inputStr The input to the command line
     * @return A new instance of the Command object requested
     * @throws DukeException If there is no matching command or the arguments do not meet the command's requirements.
     */
    public Command parse(String inputStr) throws DukeException {
        inputStr = inputStr.replace("\t", "    "); //sanitise input
        int spaceIdx = inputStr.indexOf(" ");
        int sepIdx = inputStr.indexOf(System.lineSeparator());
        String cmdStr = inputStr;
        if (!(spaceIdx == -1 && sepIdx == -1)) {
            if (spaceIdx == -1) {
                cmdStr = inputStr.substring(0, sepIdx);
            } else if (sepIdx == -1) {
                cmdStr = inputStr.substring(0, spaceIdx);
            } else {
                cmdStr = inputStr.substring(0, min(sepIdx, spaceIdx));
            }
        }
        Command command = commands.getCommand(cmdStr, context);
        if (command == null) {
            throw new DukeException("I'm sorry, but I don't recognise this command: " + cmdStr);
        }
        // TODO: autocorrect system
        // trim command and first space after it from input if needed, and standardise newlines
        if (command instanceof ArgCommand) { // stripping not required otherwise
            //standardise line separators for internal manipulation
            inputStr = inputStr.replaceAll("(\\r\\n|\\n|\\r)", "\n");
            argParser.parseArgument((ArgCommand) command, inputStr.substring(cmdStr.length()));
        }
        return command;
    }
}
