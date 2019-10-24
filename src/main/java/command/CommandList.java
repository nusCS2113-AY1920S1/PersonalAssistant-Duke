package command;

import exception.DukeException;
import list.DegreeList;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class to manage all commands that modify the taskList and degreeList.
 * Used to facilitate undoing and redoing of commands.
 * Uses a stack as it only needs the latest command.
 *
 * @author Lee Zhen Yu
 * @version 1.0
 * @since 10/16
 */
public class CommandList {
    private int undoRedoPointer = -1;
    private Stack<Command> commandList = new Stack<>();
    private Stack<String> inputList = new Stack<>();

    //Every time a new command is added, update all lists and storages
    private TaskList tasks;
    private UI ui;
    private Storage storage;
    private DegreeList lists;
    private String input;

    public CommandList() {
    }

    /**
     * Method to add a command to this list.
     * To be stored until the command needs to be undone.
     *
     * @param newCommand The command that modified the taskList.
     * @param tasks The taskList, needed for execution and unexecution.
     * @param ui The ui, needed for execution and unexecution.
     * @param storage The storage, needed for execution and unexecution.
     * @param lists The degreeList, needed for execution and unexecution.
     * @param input The user input that resulted in this command, for the user's reference.
     * @throws DukeException
     */
    public void addCommand(Command newCommand, TaskList tasks, UI ui, Storage storage, DegreeList lists, String input)
            throws DukeException, IOException {
        this.tasks = tasks;
        this.ui = ui;
        this.storage = storage;
        this.lists = lists;
        this.input = input;
        deleteElementsAfterPointer(undoRedoPointer);
        newCommand.execute(tasks, ui, storage, lists);
        commandList.push(newCommand);
        inputList.push(input);
        undoRedoPointer++;
    }

    /**
     * When a new command is added, and there are commands left that can still be redone.
     * Delete all commands after that pointer, and add the new command.
     * Once a new command has been added, we assume that the previous commands are no longer needed by the user.
     *
     * @param undoRedoPointer Pointer to the command in the stack.
     */
    private void deleteElementsAfterPointer(int undoRedoPointer) {
        if (commandList.size() < 1) return;
        for (int i = commandList.size() - 1; i > undoRedoPointer; i--) {
            commandList.remove(i);
            inputList.remove(i);
        }
    }

    /**
     * Returns the saved state of the taskList.
     *
     * @return The previous saved state of the taskList.
     */
    public TaskList getTaskList() {
        return this.tasks;
    }

    /**
     * Returns the saved state of the degreeList.
     *
     * @return The previous saved state of the degreeList.
     */
    public DegreeList getDegreeLists() {
        return this.lists;
    }

    /**
     * Method to undo the command modifying the taskList or degreeList.
     * It looks for the latest command, unexecutes it, and it will return the previous state stored in its memento.
     *
     * @throws DukeException throws when incorrect number of arguments passed.
     */
    public void undo() throws DukeException {
        if (undoRedoPointer == -1) {
            System.out.println("There are no commands to undo!");
        }
        Command command = commandList.get(undoRedoPointer);
        System.out.println("Undo this command: \"" + inputList.get(undoRedoPointer) + "\"");
        command.unExecute(tasks, ui, storage, lists);
        undoRedoPointer--;
    }

    /**
     * Method to redo the command modifying the taskList or degreeList.
     * It looks for the command pointer by undoRedoPointer and executes it.
     * This will execute the command as per normal.
     *
     * @throws DukeException throws when incorrect number of arguments passed.
     */
    public void redo() throws DukeException, IOException {
        if (undoRedoPointer == commandList.size() - 1) {
            System.out.println("There are no more commands to redo!");
        }
        undoRedoPointer++;
        Command command = commandList.get(undoRedoPointer);
        System.out.println("Redo this command: \"" + inputList.get(undoRedoPointer) + "\"");
        command.execute(tasks, ui, storage, lists);
    }
}
