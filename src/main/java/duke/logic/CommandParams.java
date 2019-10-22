package duke.logic;

import duke.exception.DukeException;
import duke.exception.DukeRuntimeException;
import duke.logic.command.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An object containing information about a command's type and parameters.
 */
public class CommandParams {
    // Internal map that stores all secondary parameters
    private final Map<String, String> secondaryParams;

    // The command type i.e. the first word in the command
    private final Command command;

    // The main parameter value i.e. everything after the first word, before any secondary parameters are declared
    private final String mainParam;

    // The regular expression used to identify secondary parameters.
    // Currently matches and replaces any number of spaces followed by a forward slash (\\s+(\\/)),
    // which are followed by any word consisting of only lowercase alphabets (not replaced).
    // Matches [and replaces]: "[ /]at", "[ /]b", "[ /]test"
    // Ignores: "1/1", "a / b", "a/ "
    private static final Pattern PARAM_INDICATOR_REGEX = Pattern.compile("(\\s+(\\/(?=[a-z]+)))");

    // The regular expression used to identify a space.
    // Currently matches and replaces any number of spaces.
    private static final Pattern SPACE_REGEX = Pattern.compile("(\\s+)");

    private static final Supplier<Stream<Command>> COMMANDS = () -> Stream.of(
        new AddExpenseCommand(),
        // new BudgetCommand(),
        new DeleteExpenseCommand(),
        new ConfirmTentativeCommand(),
        new ExitCommand(),
        new FilterExpenseCommand(),
        new SortExpenseCommand(),
        new ViewExpenseCommand(),
        new GoToCommand(),
        new PlanBotCommand()
    );

    /**
     * Creates a new {@code CommandParams} object using a {@code String} obtained directly from
     * the user. The {@code CommandParams} object cannot have two parameters of the same name, and
     * will throw a {@code DukeException} if the user tries to specify two parameters of the same name.
     *
     * @param fullCommand the full command input by the user, which will be parsed into parameters.
     * @throws DukeException if the user specified a parameter twice.
     */
    public CommandParams(String fullCommand) throws DukeException {
        secondaryParams = new HashMap<String, String>();

        // Split the input into an array of Strings, containing concatenated parameter names and values
        String[] nameValueStrings = PARAM_INDICATOR_REGEX.split(fullCommand);

        // Get commandType and mainParam first
        command = parseCommand(nameValueStrings[0]);
        mainParam = extractMainParam(nameValueStrings[0], SPACE_REGEX.split(command.getName()).length);

        // Get all the others
        for (int i = 1; i < nameValueStrings.length; i++) {
            String[] nameValuePair = SPACE_REGEX.split(nameValueStrings[i], 2);
            List<String> possibleParamNames = command.getSecondaryParams().keySet().stream()
                .filter(k -> k.startsWith(nameValuePair[0]))
                .collect(Collectors.toList());

            if (possibleParamNames.size() != 1) {
                throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_UNKNOWN, nameValuePair[0]));
            }

            String verifiedParamName = possibleParamNames.get(0);

            if (secondaryParams.containsKey(verifiedParamName)) { // can't contain the same key twice
                throw new DukeException(
                    String.format(DukeException.MESSAGE_COMMAND_PARAM_DUPLICATE, verifiedParamName));
            }

            if (nameValuePair.length == 2) {
                secondaryParams.put(verifiedParamName, nameValuePair[1]);
            } else {
                secondaryParams.put(verifiedParamName, null);
            }
        }
    }

    /**
     * Returns the command corresponding to this command params object.
     *
     * @return the command corresponding to this command params object.
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Returns the {@code mainParam} parameter that was input by the user. May be null.
     *
     * @return {@code mainParam}. May be null.
     */
    public String getMainParam() {
        return mainParam;
    }

    /**
     * Returns whether the command has a {@code mainParam}.
     *
     * @return the existence of {@code mainParam}, that is, whether it is null or not.
     */
    public boolean containsMainParam() {
        return mainParam != null;
    }

    /**
     * Returns the value of a requested parameter. The parameter's existence should be checked prior if
     * the parameter is optional, as this method throws {@code DukeException} if the parameter does not
     * exist, or is null.
     *
     * @param paramName the name of the parameter whose value to return.
     * @return the value of the requested parameter.
     * @throws DukeRuntimeException if the parameter does not exist, or is null.
     */
    public String getParam(String paramName) throws DukeException {
        String paramValue = secondaryParams.get(paramName);
        if (paramValue == null) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING_VALUE, paramName));
        } else {
            return paramValue;
        }
    }

    /**
     * Returns true if all parameters specified by {@code paramNames} exist in the {@code CommandParams}
     * object, and false otherwise.
     * <p>
     * Can be used to check for optional flags.
     *
     * @param paramNames the parameter(s) whose existence to check for.
     * @return true if the parameter(s) specified by {@code paramNames} exists, and false otherwise.
     */
    public boolean containsParams(String... paramNames) {
        for (String paramName : paramNames) {
            if (!secondaryParams.containsKey(paramName)) {
                return false;
            }
        }
        return true;
    }

    private static String extractMainParam(String string, int numberOfWords) {
        String[] words = SPACE_REGEX.split(string, numberOfWords + 1);
        if (words.length <= numberOfWords) {
            return null;
        } else {
            return words[numberOfWords];
        }
    }

    private static Command parseCommand(String commandName) throws DukeException {
        // Inelegant solution, but I don't want to have to add a new method to every Command class.
        String[] commandNameWords = Arrays.copyOfRange(commandName.split("\\s+"), 0, 2);

        if (commandNameWords.length == 2) {
            List<Command> validCommands = COMMANDS.get()
                .filter(c -> c.getName().split(" ").length == 2)
                .filter(c -> (c.getName().split(" ")[0].startsWith(commandNameWords[0])
                    && c.getName().split(" ")[1].startsWith(commandNameWords[1])))
                .collect(Collectors.toList());

            if (validCommands.size() == 1) {
                return validCommands.get(0);
            }
        }

        List<Command> validCommands = COMMANDS.get()
            .filter(c -> c.getName().split(" ").length == 1)
            .filter(c -> (c.getName().split(" ")[0].startsWith(commandNameWords[0])))
            .collect(Collectors.toList());

        if (validCommands.size() == 1) {
            return validCommands.get(0);
        }

        throw new DukeException(DukeException.MESSAGE_COMMAND_NAME_UNKNOWN);
    }
}
