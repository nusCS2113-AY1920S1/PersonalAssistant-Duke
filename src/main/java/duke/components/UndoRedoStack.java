package duke.components;

import duke.DukeException;
import duke.Parser;
import duke.commands.RedoCommand;
import duke.commands.UndoCommand;

import java.util.Stack;

public class UndoRedoStack {
    private Stack<SongList> undoRedoStack;
    private int currentVersionIndex;

    public UndoRedoStack(SongList songs) {
        undoRedoStack = new Stack<>();
        undoRedoStack.push(songs);
        currentVersionIndex = 0;
    }

    public SongList getCurrentVersion() {
        return undoRedoStack.get(currentVersionIndex);
    }

    public void add(SongList songList) {
        for (int latestVersionIndex = undoRedoStack.size() - 1;
             latestVersionIndex > currentVersionIndex; latestVersionIndex--) {
            undoRedoStack.pop();
        }
        push(songList);
    }

    public int getCurrentVersionIndex() {
        return currentVersionIndex;
    }


    private void push(SongList songList) {
        undoRedoStack.push(songList);
        currentVersionIndex++;
    }

    public boolean canUndo() {
        return currentVersionIndex > 0;
    }

    public void undo() {
        currentVersionIndex--;
    }

    public boolean canRedo() {
        return currentVersionIndex < undoRedoStack.size() - 1;
    }

    public void redo() {
        currentVersionIndex++;
    }

    public int numOfRedoLeft() {
        return undoRedoStack.size() - currentVersionIndex - 1;
    }

    // TODO: isModified to reduce change in UndoRedoStack
}
