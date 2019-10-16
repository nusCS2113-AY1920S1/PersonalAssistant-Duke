package duke.command;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ArgCommandSpec {
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

    private void switchInit(Switch... switches) {
        int switchCnt = switches.length;
        Map<String, Switch> tempSwitchMap = new HashMap<String, Switch>();
        Map<String, String> tempSwitchAliases = new HashMap<String, String>();

        // extract argument requirement levels and generate aliases
        for (int i = 0; i < switchCnt; ++i) {
            String name = switches[i].name;
            tempSwitchMap.put(name, switches[i]);

            assert(name.startsWith(switches[i].root));
            for (int j = switches[i].root.length(); j <= name.length(); ++j) {
                tempSwitchAliases.put(name.substring(0, j), name);
            }

            // extract remaining aliases
            for (String alias : switches[i].aliases) {
                tempSwitchAliases.put(alias, name);
            }
        }

        switchMap = Collections.unmodifiableMap(tempSwitchMap);
        switchAliases = Collections.unmodifiableMap(tempSwitchAliases);
    }
}
