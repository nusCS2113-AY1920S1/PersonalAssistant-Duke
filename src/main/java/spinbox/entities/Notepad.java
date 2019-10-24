package spinbox.entities;

import spinbox.Storage;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.InvalidIndexException;

import java.util.ArrayList;
import java.util.List;

public class Notepad {
    private static final String DIRECTORY_NAME = "SpinBoxData/";
    private static final String NOTEPAD_FILE_NAME = "/notes.txt";
    private static final String CLI_LIST_HEADER =  "Here are your notes: ";

    private Storage localStorage;
    List<String> notes;

    public Notepad(String parentName) throws FileCreationException {
        notes = new ArrayList<>();
        localStorage = new Storage(DIRECTORY_NAME + parentName + NOTEPAD_FILE_NAME);
    }

    /**
     * Adds a line of text to the notepad.
     * @param line A string representing one line of entered text.
     * @throws DataReadWriteException I/O error.
     */
    public void addLine(String line) throws DataReadWriteException {
        notes.add(line);
        this.saveData();
    }

    /**
     * Return note at index.
     * @param index Index of note.
     * @return Note at index.
     * @throws InvalidIndexException If index is out of range.
     */
    public String getLine(int index) throws InvalidIndexException {
        try {
            return notes.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Updates a line of text within the notepad.
     * @param index The line's index/number, starting from 0.
     * @param line A string representing one line of entered text.
     * @throws DataReadWriteException I/O error.
     * @throws InvalidIndexException The index provided is out of range.
     */
    public void updateLine(int index, String line) throws DataReadWriteException, InvalidIndexException {
        try {
            notes.set(index, line);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
        this.saveData();
    }

    /**
     * Removes one line of text within the notepad.
     * @param index The line's index/number, starting from 0.
     * @throws InvalidIndexException The index provided is out of range.
     * @throws DataReadWriteException I/O error.
     */
    public void removeLine(int index) throws DataReadWriteException, InvalidIndexException {
        try {
            notes.remove(index);
            saveData();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndexException();
        }
    }

    /**
     * Creates a list of notes with a header for CLI output.
     * @return notes with a header as element zero.
     */
    public List<String> viewList() {
        List<String> outputList = new ArrayList<>();
        outputList.add(0, CLI_LIST_HEADER);

        for (int i = 0; i < notes.size(); i++) {
            outputList.add((Integer.toString(i + 1) + ". " + notes.get(i)));
        }
        return outputList;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void loadData() throws DataReadWriteException {
        notes = localStorage.loadData();
    }

    public void saveData() throws DataReadWriteException {
        localStorage.saveData(notes);
    }
}
