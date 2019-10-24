package duke.components;

import duke.DukeException;
import duke.Parser;
import duke.commands.RedoCommand;
import duke.commands.UndoCommand;

import java.util.Stack;

//@@author SalonetheGreat
public class UndoRedoStack {
    private Stack<SongList> undoRedoStack;
    private int currentVersionIndex;

    /**
     * constructor for UndoRedoStack.
     * @param songs the song list which is going to be the first version of the undo-redo(UR) stack
     */
    public UndoRedoStack(SongList songs) {
        undoRedoStack = new Stack<>();
        undoRedoStack.push(songs);
        currentVersionIndex = 0;
    }

    /**
     * Returns the current version of the UR stack.
     * @return a song list which is the current version of the UR stack
     */
    public SongList getCurrentVersion() {
        return undoRedoStack.get(currentVersionIndex);
    }

    /**
     * Add a new version of song list into the UR stack, and move the current index to the new song list.
     * @param songList the song list to be added into the stack as the new version
     */
    public void add(SongList songList) {
        for (int latestVersionIndex = undoRedoStack.size() - 1;
             latestVersionIndex > currentVersionIndex; latestVersionIndex--) {
            undoRedoStack.pop();
        }
        push(songList);
    }

    /**
     * Returns the index indicating the current version.
     * @return a integer indicating the current version of UR stack
     */
    public int getCurrentVersionIndex() {
        return currentVersionIndex;
    }

    /**
     * Push a new song list to the top of the stack, and update the version of UR stack.
     * @param songList the song list to be put
     */
    private void push(SongList songList) {
        undoRedoStack.push(songList);
        currentVersionIndex++;
    }

    /**
     * Returns whether the UR stack can be undone or not.
     *      current version is not the first version, can undo,
     *      otherwise, cannot undo.
     * @return a boolean number indicating canUndo or not
     */
    public boolean canUndo() {
        return currentVersionIndex > 0;
    }

    /**
     * Move the index to the previous version.
     */
    public void undo() {
        currentVersionIndex--;
    }

    /**
     * Returns whether the UR stack can be redone or not.
     *      current version is not the latest version, can redo,
     *      otherwise, cannot redo.
     * @return a boolean number indicating canRedo or not
     */
    public boolean canRedo() {
        return currentVersionIndex < undoRedoStack.size() - 1;
    }

    /**
     * Move the index to the next version.
     */
    public void redo() {
        currentVersionIndex++;
    }

    /**
     * Count the number of redo times left.
     * @return a integer of number of redo times left
     */
    public int numOfRedoLeft() {
        return undoRedoStack.size() - currentVersionIndex - 1;
    }
}
