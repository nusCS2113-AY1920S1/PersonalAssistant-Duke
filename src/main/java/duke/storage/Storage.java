package duke.storage;

import duke.exceptions.DukeException;
import duke.models.LockerList;

/**
 * API for the storage.
 */
public interface Storage {

    void saveData(LockerList dataToStore) throws DukeException;

    LockerList retrieveData() throws DukeException;

    void exportAsCsv(LockerList listToExport) throws DukeException;

    void exportSelection(LockerList lockerList,String input) throws DukeException;
}
