package command;

import exception.DukeException;
import list.DegreeList;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.UI;

import java.util.ArrayList;
import java.util.Stack;

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

    public void addCommand(Command newCommand, TaskList tasks, UI ui, Storage storage, DegreeList lists, String input)
            throws DukeException {
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

    private void deleteElementsAfterPointer(int undoRedoPointer) {
        if (commandList.size() < 1) return;
        for (int i = commandList.size() - 1; i > undoRedoPointer; i--) {
            commandList.remove(i);
            inputList.remove(i);
        }
    }

    public TaskList getTaskList() {
        return this.tasks;
    }

    public DegreeList getDegreeLists() {
        return this.lists;
    }

    public void undo() throws DukeException {
        if (undoRedoPointer == -1) {
            System.out.println("There are no commands to undo!");
        }
        Command command = commandList.get(undoRedoPointer);
        System.out.println("Undo this command: \"" + inputList.get(undoRedoPointer) + "\"");
        command.unExecute(tasks, ui, storage, lists);
        undoRedoPointer--;
    }

    public void redo() throws DukeException {
        if (undoRedoPointer == commandList.size() - 1) {
            System.out.println("There are no more commands to redo!");
        }
        undoRedoPointer++;
        Command command = commandList.get(undoRedoPointer);
        System.out.println("Redo this command: \"" + inputList.get(undoRedoPointer) + "\"");
        command.execute(tasks, ui, storage, lists);
    }
}
