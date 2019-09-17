package command;

import parser.CommandParams;
import task.TaskList;
import ui.Ui;
import storage.Storage;

/**
 * Represents a command packaged by class <code>Parser</code>.
 * Works as a parent class of more specified command classes in the package.
 */
public class Command {
    protected CommandParams commandParams;

    /**
     * Constructs a <code>Command</code> object with commandType.
     *
     * @param commandParams parameters used to invoke the command.
     */
    protected Command(CommandParams commandParams){
        this.commandParams = commandParams;
    }

    /**
     * Executes the command.
     * Works as an empty method of parent class to be overridden.
     *
     * @param tasks The taskList of Duke.
     * @param ui The ui of Duke.
     * @param storage The storage of Duke.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // to be overridden
    }

    /**
     * Returns true if commandType is "exit" and false otherwise.
     * Decides whether the loop in <code>main</code> method of Duke terminates.
     *
     * @return The boolean indicating whether quit the loop in <code>main</code> method.
     */
    public boolean isExit() {
        if (commandParams.getCommandType().equals("exit")) {
            return true;
        } else {
            return false;
        }
    }
}
