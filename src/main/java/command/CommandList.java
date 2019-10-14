package command;

import exception.DukeException;
import list.DegreeList;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.util.ArrayList;
import java.util.Stack;

public class CommandList {
    private int undoRedoPointer = -1;
    private Stack<Command> commandList = new Stack<>();

    public CommandList() {
    }

    private void addCommand(Command newCommand, TaskList tasks, UI ui, Storage storage, DegreeList lists)
            throws DukeException {
        deleteElementsAfterPointer(undoRedoPointer);
        newCommand.execute(tasks, ui, storage, lists);
        commandList.push(newCommand);
        undoRedoPointer++;
    }

    private void deleteElementsAfterPointer(int undoRedoPointer) {
        if (commandList.size() < 1) return;
        for (int i = commandList.size() - 1; i > undoRedoPointer; i--) {
            commandList.remove(i);
        }
    }

    private void undo() {
        Command command = commandList.get(undoRedoPointer);
        command.unExecute();
        undoRedoPointer--;
    }

    private void redo() {
        if (undoRedoPointer == commandList.size() - 1)
            return;
        undoRedoPointer++;
        Command command = commandList.get(undoRedoPointer);


    }
}
