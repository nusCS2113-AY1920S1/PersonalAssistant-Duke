package parser;

import exception.DukeException;

import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

/**
 * An object containing information about a command's type and parameters.
 */
public class CommandParams {
    // Internal map that stores all secondary parameters
    private Map<String, String> secondaryParams;

    // The command type i.e. the first word in the command
    private String commandName;

    // The main parameter value i.e. everything after the first word, before any secondary parameters are declared
    private String mainParam;

    // The regular expression used to identify secondary parameters.
    // Currently matches and replaces any number of spaces followed by a forward slash (\\s+(\\/)),
    // which are followed by any word consisting of only lowercase alphabets (not replaced).
    // Matches [and replaces]: "[ /]at", "[ /]b", "[ /]test"
    // Ignores: "1/1", "a / b", "a/ "
    private static final Pattern PARAM_INDICATOR_REGEX = Pattern.compile("(\\s+(\\/(?=[a-z]+)))");

    // The regular expression used to identify a space.
    // Currently matches and replaces any number of spaces.
    private static final Pattern SPACE_REGEX = Pattern.compile("(\\s+)");

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
        String[] typeAndMainParam = SPACE_REGEX.split(nameValueStrings[0], 2);
        commandName = typeAndMainParam[0];
        if (typeAndMainParam.length == 2) { // has main param
            mainParam = typeAndMainParam[1];
        } else {
            mainParam = null;
        }

        // Get all the others
        for (int i = 1; i < nameValueStrings.length; i++) {
            String[] nameValuePair = SPACE_REGEX.split(nameValueStrings[i], 2);
            if (secondaryParams.containsKey(nameValuePair[0])) { // can't contain the same key twice
                throw new DukeException(String.format("☹ OOPS!!! You specified %1$s twice!", nameValuePair[0]));
            }

            if (nameValuePair.length == 2) {
                secondaryParams.put(nameValuePair[0], nameValuePair[1]);
            } else {
                secondaryParams.put(nameValuePair[0], null);
            }
        }
    }

    /**
     * Returns the {@code commandType} parameter that was input by the user.
     *
     * @return {@code commandType}.
     */
    public String getCommandName() {
        return commandName;
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
     * Returns the value of a requested parameter. This function should be used to request mandatory parameters,
     * and will throw a {@code DukeException} if the parameter does not exist, or is null.
     *
     * @param paramName the name of the parameter whose value to return.
     * @return the value of the requested parameter.
     * @throws DukeException if the parameter does not exist, or is null.
     */
    public String getParam(String paramName) throws DukeException {
        String paramValue = secondaryParams.get(paramName);
        if (paramValue == null) {
            throw new DukeException(String.format("☹ OOPS!!! You need to give me a value for %1$s!", paramName));
        } else {
            return paramValue;
        }
    }

    /**
     * Returns the value of a requested parameter. This function should be used to request optional parameters,
     * and returns {@code null} if the parameter does not exist, or contains no value.
     *
     * @param paramName the name of the parameter whose value to return.
     * @return the value of the requested parameter. May be null.
     */
    public String getOptionalParam(String paramName) {
        return secondaryParams.get(paramName);
    }

    /**
     * Returns true if all parameters specified by {@code paramNames} exist in the {@code CommandParams}
     * object, and false otherwise.
     *
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
}
