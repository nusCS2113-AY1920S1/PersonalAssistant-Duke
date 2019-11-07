package duke.command;

import duke.DukeCore;
import duke.exception.DukeException;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Abstract class for the argument specification for a particular class. Although Java does not provide a
 * mechanism to enforce or document this contract, any subclass of ArgSpec must have a private constructor which sets
 * {@code cmdArgLevel} and {@code emptyArgMsg}, and which calls {@code switchInit()}.
 */
public abstract class ArgSpec extends CommandSpec {
    protected ArgLevel cmdArgLevel;
    protected Map<String, Switch> switchMap;
    protected Map<String, String> switchAliases;
    protected ArgCommand cmd = null;

    /**
     * Set {@code cmd} to the supplied command, exposing access to the switches supplied to it, and perform the
     * operations specified by this CommandSpec object's {@code execute(DukeCore core)} method, using the supplied
     * switches to modify behaviour.
     *
     * @param core The DukeCore object to use to execute the command.
     * @param cmd The ArgCommand instance being executed, holding the relevant switches.
     * @see DukeCore
     * @see ArgCommand
     * @throws DukeException If an error occurs during command execution.
     */
    public void executeWithCmd(DukeCore core, ArgCommand cmd) throws DukeException {
        this.cmd = cmd;
        execute(core);
        this.cmd = null;
    }

    @Override
    protected void execute(DukeCore core) throws DukeException {
        assert (cmd != null);
    }

    public ArgLevel getCmdArgLevel() {
        return cmdArgLevel;
    }

    public Map<String, Switch> getSwitchMap() {
        return switchMap;
    }

    public Map<String, String> getSwitchAliases() {
        return switchAliases;
    }

    protected void initSwitches(Switch... switches) {
        Map<String, Switch> tempSwitchMap = new HashMap<String, Switch>();
        Map<String, String> tempSwitchAliases = new HashMap<String, String>();
        Set<String> switchRootSet = new HashSet<String>();

        for (Switch currSwitch : switches) {
            // create map of switch names to switch objects
            String name = currSwitch.name;
            assert (!tempSwitchMap.containsKey(name));
            tempSwitchMap.put(name, currSwitch);

            // extract prefixes to build lookup table
            assert (name.startsWith(currSwitch.root));
            assert (!switchRootSet.contains(currSwitch.root));
            switchRootSet.add(currSwitch.root);
            for (int j = currSwitch.root.length(); j <= name.length(); ++j) {
                tempSwitchAliases.put(name.substring(0, j), name);
            }

            // extract remaining aliases
            for (String alias : currSwitch.aliases) {
                tempSwitchAliases.put(alias, name);
            }
        }

        switchMap = Collections.unmodifiableMap(tempSwitchMap);
        switchAliases = Collections.unmodifiableMap(tempSwitchAliases);
    }
}
