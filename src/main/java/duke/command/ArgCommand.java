package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;
import javafx.util.Pair;

import java.util.ArrayList;
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
        // input string should not have been empty, so at least one of these should have been set
        if (arg == null && switchVals.isEmpty()) {
            throw new DukeException("Command needs to parse argument first!");
        }
    }

    void setSwitchValsMap(HashMap<String, String> switchVals) {
        this.switchVals.putAll(switchVals);
    }

    void setSwitchVal(String switchName, String value) {
        switchVals.put(switchName, value);
    }

    String getSwitchVal(String switchName) {
        return switchVals.get(switchName);
    }

    void setArg(String arg) {
        this.arg = arg;
    }

    String getArg() {
        return arg;
    }

    abstract ArgCommandSpec getSpec();

    public String getEmptyArgMsg() {
        return getSpec().emptyArgMsg;
    }

    public ArgLevel getCmdArgLevel() {
        return getSpec().cmdArgLevel;
    }

    public Map<String, Switch> getSwitchMap() {
        return getSpec().switchMap;
    }

    public Map<String, String> getSwitchAliases() {
        return getSpec().switchAliases;
    };
}
