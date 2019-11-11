package duke.storage;

import duke.exceptions.DukeException;

import duke.models.LockerList;

import java.util.ArrayList;

/**
 * API for the storage.
 */
public interface Storage {

    void saveData(LockerList dataToStore) throws DukeException;

    LockerList retrieveData() throws DukeException;

    void exportAsCsv(LockerList listToExport) throws DukeException;

    void exportSelection(LockerList lockerList, String input) throws DukeException;

    void initializeStateList(LockerList lockerList) throws DukeException;

    void updateStateList(LockerList lockerList) throws DukeException;

    LockerList undoStateList() throws DukeException;

    LockerList redoStateList() throws DukeException;

    void updateHistoryList(String cmd) throws DukeException;

    ArrayList<String> getHistoryList() throws DukeException;
}
