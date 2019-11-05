package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    // TODO: reduce coupling; refactor ArgCommand to have an interactive builder mode?
    private String arg; //argument supplied to the command
    private HashMap<String, String> switchVals; //hashmap of switch parameters

    public ArgCommand() {
         arg = null;
         switchVals = new HashMap<String, String>();
    }

    public ArgCommand(String arg, String[] switchNames, String[] switchVals) throws DukeException {
        this();
        setArg(arg);
        if (switchNames.length != switchVals.length) {
            throw new DukeException("You gave me " + switchNames.length + " switch names, but "
                    + switchVals.length + " switch values!");
        }
        for (int i = 0; i < switchNames.length; ++i) {
            setSwitchVal(switchNames[i], switchVals[i]);
        }
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        // do any necessary pre-processing
    }

    protected void setSwitchValsMap(Map<String, String> switchVals) {
        this.switchVals.putAll(switchVals);
    }

    protected void setSwitchVal(String switchName, String value) throws DukeHelpException {
        Switch newSwitch = getSwitchMap().get(switchName);
        if (newSwitch == null) {
            throw new DukeHelpException("I don't know what the '" + switchName + "' switch is!", this);
        }

        if (newSwitch.argLevel == ArgLevel.NONE && value != null) {
            throw new DukeHelpException("The '" + switchName + "' switch should not have an argument!", this);
        }

        if (newSwitch.argLevel == ArgLevel.REQUIRED && value == null) {
            throw new DukeHelpException("The '" + switchName + "' switch should have an argument!", this);
        }
        switchVals.put(switchName, value);
    }

    protected String getSwitchVal(String switchName) {
        return switchVals.get(switchName);
    }

    protected boolean isSwitchSet(String switchName) {
        return switchVals.containsKey(switchName);
    }

    protected void setArg(String arg) throws DukeHelpException {
        ArgLevel cmdArgLevel = getCmdArgLevel();
        if (cmdArgLevel == ArgLevel.REQUIRED) {
            if (arg == null) {
                throw new DukeHelpException("This command requires an argument!", this);
            }
        } else if (cmdArgLevel == ArgLevel.NONE) {
            if (arg != null) {
                throw new DukeHelpException("This command should not have an argument!", this);
            }
        }
        this.arg = arg;
    }

    protected String getArg() {
        return arg;
    }

    protected abstract ArgSpec getSpec();

    // I hate Java

    protected String getEmptyArgMsg() {
        return getSpec().getEmptyArgMsg();
    }

    protected ArgLevel getCmdArgLevel() {
        return getSpec().getCmdArgLevel();
    }

    protected Map<String, Switch> getSwitchMap() {
        return getSpec().getSwitchMap();
    }

    protected Map<String, String> getSwitchAliases() {
        return getSpec().getSwitchAliases();
    }

    protected Map<String, String> getSwitchVals() {
        return Collections.unmodifiableMap(switchVals);
    }

    /**
     * Checks if a particular switch, and if not, attempts to parse it as an Integer.
     * @param switchName The name of the switch being extracted.
     * @return The Integer that the string represents, or -1 if it is null.
     * @throws NumberFormatException If the string is not a valid representation of an integer.
     */
    protected Integer switchToInt(String switchName) throws DukeHelpException {
        String str = this.getSwitchVal(switchName);
        if (str == null) {
            return -1;
        } else {
            try {
                Integer parseInt = Integer.parseInt(str);
                // TODO document this
                if (parseInt < 0) {
                    throw new DukeHelpException("The value of '" + switchName + "' cannot be negative!", this);
                }
                return parseInt;
            } catch (NumberFormatException excp) {
                throw new DukeHelpException("The switch '" + switchName + "' must be an integer!", this);
            }
        }
    }

    /**
     * Sets the arguments for optional switches that require String-type arguments to the empty String.
     * NOTE: Switches with ArgLevel.OPTIONAL are ignored by this method.
     */
    protected void nullToEmptyString() throws DukeException {
        for (Map.Entry<String, Switch> entry : getSwitchMap().entrySet()) {
            String switchName = entry.getKey();
            Switch switchSpec = entry.getValue();
            if (getSwitchVal(switchName) == null && switchSpec.type == String.class && switchSpec.argLevel
                    == ArgLevel.REQUIRED) {
                setSwitchVal(switchName, "");
            }
        }
    }

    /**
     * Checks for equality with another ArgCommand object, inspecting arguments and switches.
     *
     * @param other The ArgCommand object to check against.
     * @return True if all switches that are set in one ArgCommand are set in the other, and if all switches that are
     *     set are set to the same value.
     */
    public boolean equals(ArgCommand other) {
        if (getClass() != other.getClass()) {
            return false;
        }

        if (!CommandUtils.equalsIfNotBothNull(arg, other.arg)) {
            return false;
        }

        Map<String, String> thisSwitchVals = getSwitchVals();
        Map<String, String> otherSwitchVals = other.getSwitchVals();
        for (String switchName : getSwitchMap().keySet()) {
            if ((otherSwitchVals.containsKey(switchName) && !thisSwitchVals.containsKey(switchName))
                    || (!otherSwitchVals.containsKey(switchName) && thisSwitchVals.containsKey(switchName))
                    || !CommandUtils.equalsIfNotBothNull(thisSwitchVals.get(switchName),
                    otherSwitchVals.get(switchName))) {
                return false;
            }
        }
        return true;
    }
}
