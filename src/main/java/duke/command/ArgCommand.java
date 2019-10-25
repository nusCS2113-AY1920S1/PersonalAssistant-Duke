package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

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
        // ..
    }

    protected void setSwitchValsMap(HashMap<String, String> switchVals) {
        this.switchVals.putAll(switchVals);
    }

    protected void setSwitchVal(String switchName, String value) {
        switchVals.put(switchName, value);
    }

    protected String getSwitchVal(String switchName) {
        return switchVals.get(switchName);
    }

    protected void setArg(String arg) {
        this.arg = arg;
    }

    protected String getArg() {
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

    ;
}
