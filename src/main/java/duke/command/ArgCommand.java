package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    protected static Map<String, ArgLevel> switches; //list of recognised switches, and their argument requirements
    protected static TreeSet switchTree; //tree of switches, used for fast lookup for autocomplete
    private String arg = null; //argument supplied to the command
    private HashMap<String, String> switchVals = new HashMap<String, String>(); //hashmap of switch parameters

    @Override
    public void execute(DukeCore core) throws DukeException {
        if (arg == null) {
            throw new DukeException("Command needs to parse argument first!");
        }
    }

    Map<String, ArgLevel> getSwitches() {
        return switches;
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

    // Call after static initialization of switches
    protected static void switchInit() {
        switchTree = new TreeSet<String>(switches.keySet());
    }

    // Override these methods to specify parameters of child classes
    abstract String getEmptyArgMsg();
    abstract ArgLevel getCmdArgLevel();
}
