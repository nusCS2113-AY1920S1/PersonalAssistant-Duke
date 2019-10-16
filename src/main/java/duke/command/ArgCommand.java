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
    protected static HashMap switchAliases;
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

    /**
     * Sets up the static data structures of the class using parameters provided. All arrays must be of the same length.
     * Unchecked assignments are used to generate arrays of entries for the argument level and switch root maps.
     *
     * @param switchNameArr The names of the switches for this command.
     * @param argLevelArr The argument requirement level for each command.
     * @param switchRootArr The shortest recognised truncation of each switch.
     */
    protected static void switchInit(String[] switchNameArr, ArgLevel[] argLevelArr, String[] switchRootArr) {
        assert(switchNameArr.length == argLevelArr.length);
        assert(switchRootArr.length == switchNameArr.length);
        int switchCnt = switchNameArr.length;
        Map.Entry[] argLevelEntries = new Map.Entry[switchCnt];
        Map.Entry[] switchRootEntries = new Map.Entry[switchCnt];
        for (int i = 0; i < switchCnt; ++i) {
            argLevelEntries[i] = Map.entry(switchNameArr[i], argLevelArr[i]);
            switchRootEntries[i] = Map.entry(switchNameArr[i], switchRootArr[i]);
        }
        switches = Map.ofEntries(argLevelEntries);
        switchTree = new TreeSet<String>(switches.keySet());
        //TODO: generate switchAliases, and figure out how to map extra switch aliases from each command
    }

    // Override these methods to specify parameters of child classes
    abstract String getEmptyArgMsg();
    abstract ArgLevel getCmdArgLevel();
}
