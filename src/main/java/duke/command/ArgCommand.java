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

    // TODO: create <Command Type>Spec singletons that are each attached to individual Command types
    // Use internal protected classes to specify the properties of switches: optional or not, whether it takes
    // an argument, and whether it is an integer or a string

    private String arg = null; //argument supplied to the command
    private HashMap<String, String> switchVals = new HashMap<String, String>(); //hashmap of switch parameters

    @Override
    public void execute(DukeCore core) throws DukeException {
        // do any necessary pre-processing
    }

    public void setSwitchValsMap(Map<String, String> switchVals) {
        this.switchVals.putAll(switchVals);
    }

    public void setSwitchVal(String switchName, String value) {
        switchVals.put(switchName, value);
    }

    public String getSwitchVal(String switchName) {
        return switchVals.get(switchName);
    }

    public boolean isSwitchSet(String switchName) {
        return switchVals.containsKey(switchName);
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public String getArg() {
        return arg;
    }

    protected abstract ArgSpec getSpec();

    // I hate Java

    public String getEmptyArgMsg() {
        return getSpec().getEmptyArgMsg();
    }

    public ArgLevel getCmdArgLevel() {
        return getSpec().getCmdArgLevel();
    }

    public Map<String, Switch> getSwitchMap() {
        return getSpec().getSwitchMap();
    }

    public Map<String, String> getSwitchAliases() {
        return getSpec().getSwitchAliases();
    }

    public Map<String, String> getSwitchVals() {
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
    protected void nullToEmptyString() {
        for (Map.Entry<String, Switch> entry : getSwitchMap().entrySet()) {
            String switchName = entry.getKey();
            Switch switchSpec = entry.getValue();
            if (getSwitchVal(switchName) == null && switchSpec.type == String.class && switchSpec.argLevel
                    == ArgLevel.REQUIRED) {
                setSwitchVal(switchName, "");
            }
        }
    }

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
            if (!CommandUtils.equalsIfNotBothNull(thisSwitchVals.get(switchName), otherSwitchVals.get(switchName))) {
                return false;
            }
        }
        return true;
    }


}
