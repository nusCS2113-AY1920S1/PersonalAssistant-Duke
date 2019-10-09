package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.HashMap;

/**
 * Abstract class for commands that involve an argument.
 */
public abstract class ArgCommand extends Command {

    protected ArgLevel cmdArgLevel; //whether or not the command itself is required to have an argument
    protected String arg = null; //argument supplied to the command
    protected String emptyArgMsg; //error message if the argument is empty
    protected HashMap<String, String> switchVals = new HashMap<String, String>(); //hashmap of switch parameters
    protected HashMap<String, ArgLevel> switches = new HashMap<String, ArgLevel>(); //list of recognised switches

    @Override
    public void execute(DukeCore core) throws DukeException {
        if (arg == null) {
            throw new DukeException("Command needs to parse argument first!");
        }
    }

    String getEmptyArgMsg() {
        return emptyArgMsg;
    }

    HashMap<String, ArgLevel> getSwitches() {
        return switches;
    }

    void setSwitchVals(HashMap<String, String> switchVals) {
        this.switchVals.putAll(switchVals);
    }
}
