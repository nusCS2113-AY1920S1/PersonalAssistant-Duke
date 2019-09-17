package parser;

import exception.DukeException;

import java.util.HashMap;
import java.util.Map;

public class CommandParams {
    private Map<String, String> paramMap;
    private String commandType;
    private String mainParam;

    @SuppressWarnings({"chekstyle:SummaryJavadoc", "checkstyle:MissingJavadocMethod"})
    /**
     * Returns a <code>Map<String, String></code> containing all the parameters input by the user.
     *
     * @param fullCommand the full command input by the user, which will be parsed into parameters.
     * @throws DukeException if the user specified a parameter twice.
     */
    public CommandParams(String fullCommand) throws DukeException {
        // Split the full command at " /paramName", keeping paramName intact.
        // Full conditions for matching require a space before the slash,
        // and a paramName that is purely alphabetical.
        // Matches: " /at", " /b", " /test" Ignores: "1/1", "a / b", "a/ "
        paramMap = new HashMap<String, String>();
        String[] tokens = fullCommand.split("(\\s+(\\/(?=[A-Za-z]+)))");

        // Get commandType and mainParam first
        String[] typeAndMainParam = tokens[0].split("(\\s+)", 2);
        commandType = typeAndMainParam[0];
        if (typeAndMainParam.length == 2) { // has main param
            mainParam = typeAndMainParam[1];
        } else {
            mainParam = null;
        }

        // Get all the others
        for (int i = 1; i < tokens.length; i++) {
            // Split the token into parameterName and parameterValue aka paramParams
            String[] paramParams = tokens[i].split("(\\s+)", 2);
            if (paramMap.containsKey(paramParams[0])) { // can't contain the same key twice
                throw new DukeException(String.format("☹ OOPS!!! You specified %1$s twice!", paramParams[0]));
            }

            if (paramParams.length == 1) {
                // parameter without value; possibly a flag (don't set to null?)
                paramMap.put(paramParams[0], null);
            } else {
                paramMap.put(paramParams[0], paramParams[1]);
            }
        }
    }

    @SuppressWarnings("checkstyle:NonEmptyAtclauseDescription")
    /**
     * Returns the <code>commandType</code> parameter that was input by the user.
     *
     * @return <code>commandType</code>.
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Returns the <code>mainParam</code> parameter that was input by the user. May be null.
     *
     * @return <code>mainParam</code>. May be null.
     */
    public String getMainParam() {
        return mainParam;
    }

    /**
     * Returns the value of a requested parameter. Use <code>containsParam()</code> to check
     * for existence of an optional parameter, as this method will throw an exception if
     * the parameter was not provided by the user; calling this method implies that the
     * parameter must exist at the point where it was invoked. May be null.
     *
     * @param paramName the name of the parameter whose value to return.
     * @return the value of the requested parameter. May be null.
     * @throws DukeException if the parameter does not exist.
     */
    public String getParam(String paramName) throws DukeException {
        if (containsParam(paramName)) {
            return paramMap.get(paramName);
        } else {
            throw new DukeException(String.format("☹ OOPS!!! You need to give me a value for %1$s!", paramName));
        }
    }

    /**
     * Returns true if the parameter specified by <code>paramName</code> exists, and false otherwise.
     * Used to check if an optional parameter exists, or for parameters that act as flags with no value.
     *
     * @param paramName the parameter whose existence to check for.
     * @return true if the parameter specified by <code>paramName</code> exists, and false otherwise.
     */
    public boolean containsParam(String paramName) {
        return paramMap.containsKey(paramName);
    }
}
