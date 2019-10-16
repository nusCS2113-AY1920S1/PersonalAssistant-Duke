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
        if (arg == null) {
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

    /**
     * Returns the static data structures of the class using parameters provided. All arrays must be of the same length.
     * Unchecked assignments are used to generate arrays of entries for the argument level and switch root maps.
     *
     * @param switchNameArr The names of the switches for this command.
     * @param argLevelArr The argument requirement level for each command.
     * @param switchRootArr The shortest recognised truncation of each switch.
     * @param aliases An array containing an even number of strings. Each pair of strings is added as a key-value
     * pair to the switchAliases map.
     */
    @SuppressWarnings("unchecked")
    protected static Pair<Map<String, ArgLevel>, Map<String, String>> switchInit(String[] switchNameArr,
            ArgLevel[] argLevelArr, String[] switchRootArr, String... aliases) {
        assert(switchNameArr.length == argLevelArr.length);
        assert(switchRootArr.length == switchNameArr.length);
        assert(aliases.length % 2 == 0);

        int switchCnt = switchNameArr.length;
        Map.Entry[] argLevelEntries = new Map.Entry[switchCnt];
        ArrayList<Map.Entry<String, String>> entryArrList = new ArrayList<Map.Entry<String, String>>();

        // extract argument requirement levels and generate aliases
        for (int i = 0; i < switchCnt; ++i) {
            String name = switchNameArr[i];
            argLevelEntries[i] = Map.entry(name, argLevelArr[i]);
            assert(name.startsWith(switchRootArr[i]));
            for (int j = switchRootArr[i].length(); j < name.length(); ++j) {
                entryArrList.add(Map.entry(name.substring(0, j), name));
            }
        }

        // extract remaining aliases
        for (int k = 0; k < aliases.length; ++k) {
            entryArrList.add(Map.entry(aliases[k], aliases[k + 1]));
        }

        // translate the ArrayList into an array
        Map.Entry[] entryArr = new Map.Entry[entryArrList.size()];
        for (int l = 0; l < entryArr.length; ++l) {
            entryArr[l] = entryArrList.get(l);
        }

        // create static data structures
        return new Pair<Map<String, ArgLevel>, Map<String, String>> (
                Map.ofEntries(entryArr), Map.ofEntries(argLevelEntries));
    }

    // Override these methods to specify parameters of child classes
    abstract String getEmptyArgMsg();
    abstract ArgLevel getCmdArgLevel();
    abstract Map<String, ArgLevel> getSwitches();
    abstract Map<String, String> getSwitchAliases();
}
