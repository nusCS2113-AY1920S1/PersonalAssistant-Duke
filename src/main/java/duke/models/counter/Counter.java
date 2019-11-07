package duke.models.counter;

import duke.commands.Command;
import duke.commands.functional.DukeCommand;
import duke.commands.functional.ExitCommand;
import duke.commands.functional.HelpCommand;
import duke.commands.functional.PieChartCommand;
import duke.commands.functional.UndoCommand;
import duke.exceptions.DukeException;
import duke.storages.StorageManager;

import java.util.Map;

//@@author qjie7

/**
 * This is a Counter class that mainly used for counting purpose.
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
     * @param command the command type that is being processed
     * @param counter get the Counter object
     * @author QIAN JIE
     * @version 1.3
     */

    public void runCommandCounter(Command command, StorageManager storageManager,
                                  Counter counter) throws DukeException {
        if (!(command instanceof ExitCommand || command instanceof DukeCommand || command instanceof UndoCommand
            || command instanceof HelpCommand || command instanceof PieChartCommand)) {
            String commandName = command.getClass().getSimpleName();
            runCounterLogic(commandName);
            storageManager.saveCounters(counter.getCommandTable());
        }
    }

    /**
     * This function is used to run the counter logic.
     *
     * @param commandName the command name of the class being called
     * @author QIAN JIE
     * @version 1.3
     */
    public void runCounterLogic(String commandName) {
        int count = commandTable.containsKey(commandName)
                    ? commandTable.get(commandName) : 0;
        commandTable.put(commandName, count + 1);
    }
}
//@author
