package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    protected static ArgLevel cmdArgLevel; //whether or not the command itself is required to have an argument
    protected static String arg = null; //argument supplied to the command
    protected static String emptyArgMsg; //error message if the argument is empty
    protected static HashMap<String, String> switchVals = new HashMap<String, String>(); //hashmap of switch parameters
    protected static Map<String, ArgLevel> switches; //list of recognised switches

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

    void setSwitchVals(HashMap<String, String> switchVals) {
        this.switchVals.putAll(switchVals);
    }
}
