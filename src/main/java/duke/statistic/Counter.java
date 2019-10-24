package duke.statistic;

import duke.command.Command;
import duke.command.DukeCommand;
import duke.command.ExitCommand;
import duke.core.DukeException;
import duke.storage.StorageManager;

import java.util.Map;

//@@author qjie7

/**
 * This is a Counter class that mainly used for counting purpose.
 * @author QIAN JIE
 * @version 1.3
 */
public class Counter {
    private Map<String, Integer> commandTable;

    public Counter(Map<String, Integer> commandTable) {
        this.commandTable = commandTable;
    }

    public Map<String, Integer> getCommandTable() {
        return commandTable;
    }

    /**
     * This function is used to run the command counter.
     *
     * @param command the command type that is being processed
     * @param counter get the Counter object
     * @author QIAN JIE
     * @version 1.3
     */

    public void runCommandCounter(Command command, StorageManager storageManager,
                                  Counter counter) throws DukeException {
        if (!(command instanceof ExitCommand || command instanceof DukeCommand)) {
            String commandName = command.getClass().getSimpleName();

            int count = commandTable.containsKey(commandName)
                    ? commandTable.get(commandName) : 0;

            commandTable.put(commandName, count + 1);
            storageManager.saveCounters(counter.getCommandTable());
        }

    }
}
