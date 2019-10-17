package duke.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ArgSpec {
    protected String emptyArgMsg;
    protected ArgLevel cmdArgLevel;
    protected Map<String, Switch> switchMap;
    protected Map<String, String> switchAliases;

    public ArgLevel getCmdArgLevel() {
        return cmdArgLevel;
    }

    public Map<String, Switch> getSwitchMap() {
        return switchMap;
    }

    public Map<String, String> getSwitchAliases() {
        return switchAliases;
    }

    public String getEmptyArgMsg() {
        return emptyArgMsg;
    }

    protected void initSwitches(Switch... switches) {
        Map<String, Switch> tempSwitchMap = new HashMap<String, Switch>();
        Map<String, String> tempSwitchAliases = new HashMap<String, String>();

        for (Switch currSwitch : switches) {
            // create map of switch names to switch objects
            String name = currSwitch.name;
            tempSwitchMap.put(name, currSwitch);

            // extract prefixes to build lookup table
            assert (name.startsWith(currSwitch.root));
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
