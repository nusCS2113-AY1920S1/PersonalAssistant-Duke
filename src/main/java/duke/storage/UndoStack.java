package duke.storage;

import java.util.Stack;

import duke.command.Command;

/**
 * Class UndoStack which contains all the mirror commands of commands which can be undone
 * UndoStack is instantiated to be new each time user opens the application.
 * UndoStack does not save the previous undone operations from the past sessions.
 */
public class UndoStack {
    private Stack<Command> memory;

    /**
     * Creates a new empty UndoStack
     * Underlying data structure is a java.util.Stack of Command
     */
    public UndoStack() {
        memory = new Stack<>();
    }

    /**
     * Adds a mirror command to undo the command that user just executed if the command can be undone
     *
     * @param action mirror command
     */
    public void addAction(Command action) {
        memory.push(action);
    }

    /**
     * Retrieves the most recent mirror command to enable to undo procedure of the most recent command that can be
     * undone.
     *
     * @return most recent mirror command
     */
    public Command retrieveRecent() {
        return memory.pop();
    }

    /**
     * Checks if there are any mirror commands in UndoStack. If there are none, then user calling undo should not do
     * anything. This facilitates that process.
     *
     * @return boolean of true when UndoStack is empty else false
     */
    public boolean isEmpty() {
        return memory.isEmpty();
    }
}
