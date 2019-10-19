package duke.statistic;

import java.util.Map;

public class CommandCounter {
    private Map<String, Integer> commandTable;

    public CommandCounter(Map<String, Integer> commandTable) {
        this.commandTable = commandTable;
    }

    public Map<String, Integer> getCommandTable() {
        return commandTable;
    }

    /**
     * This function is used to run the command counter.
     * @param commandTable a table with command name as key and count as value
     * @param commandName the command name that was used
     * @author QIAN JIE
     * @version 1.3
     */
    public void runCommandCounter(Map<String, Integer> commandTable, String commandName) {
        int count = commandTable.containsKey(commandName)
                ? commandTable.get(commandName) : 0;
        commandTable.put(commandName, count + 1);
    }


}
