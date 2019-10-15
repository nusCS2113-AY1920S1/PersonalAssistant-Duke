package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    // TODO: replace with getters and setters
    protected static String emptyArgMsg; //error message if the argument is empty
    protected static ArgLevel cmdArgLevel; //whether or not the command itself is required to have an argument
    protected static Map<String, ArgLevel> switches; //list of recognised switches, and their argument requirements
    private String arg = null; //argument supplied to the command
    private HashMap<String, String> switchVals = new HashMap<String, String>(); //hashmap of switch parameters

    @Override
    public void execute(DukeCore core) throws DukeException {
        if (arg == null) {
            throw new DukeException("Command needs to parse argument first!");
        }
    }

    String getEmptyArgMsg() {
        return emptyArgMsg;
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

    ArgLevel getCmdArgLevel() {
        return cmdArgLevel;
    }
}
