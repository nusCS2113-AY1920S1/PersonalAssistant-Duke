package duke.statistic;

import duke.command.Command;
import duke.core.DukeException;
import duke.storage.CounterStorage;

import java.util.Map;

/**
 * This is a Counter class that mainly used for counting purpose
 * which can then to be used for relevant application.
 *
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
     * @param command        the command type that is being processed
     * @param counterStorage get the counterStorage object
     * @param counter get the Counter object
     * @author QIAN JIE
     * @version 1.3
     */

    public void runCommandCounter(Command command, CounterStorage counterStorage,
                                  Counter counter) throws DukeException {
        String commandName = command.getClass().getSimpleName();

        int count = commandTable.containsKey(commandName)
                    ? commandTable.get(commandName) : 0;
        commandTable.put(commandName, count + 1);
        counterStorage.save(counter.getCommandTable());
    }


}
