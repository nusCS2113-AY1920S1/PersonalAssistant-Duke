package duke.storage;

import duke.exceptions.DukeException;

import duke.models.LockerList;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

/**
 * Manages storage of SpongeBob data in local storage and exports data to Csv files.
 */
public class StorageManager implements Storage {

    private FileStorage fileStorage;
    private ExportCsv writeToCsv;
    private ExportSelection selectionCsv;
    private StateList stateList;
    private CommandHistoryList historyList;

    /**
     * This function managers storage data from the file.
     */
    public StorageManager(String fileName) {
        fileStorage = new FileStorage(fileName);
        writeToCsv = new ExportCsv();
        selectionCsv = new ExportSelection();
        stateList = new StateList();
        historyList = new CommandHistoryList();
    }

    @Override
    public void saveData(LockerList dataToStore) throws DukeException {
        requireNonNull(dataToStore);
        fileStorage.saveData(dataToStore);
    }

    @Override
    public LockerList retrieveData() throws DukeException {
        return fileStorage.retrieveData();
    }

    @Override
    public void exportAsCsv(LockerList lockerList) throws DukeException {
        requireNonNull(lockerList);
        writeToCsv.exportLockers(lockerList.getLockerList());
    }

    @Override
    public void exportSelection(LockerList lockerList, String input) throws DukeException {
        requireNonNull(lockerList);
        selectionCsv.exportSelect(lockerList.getLockerList(),input);
    }


    @Override
    public void initializeStateList(LockerList lockerList) {
        stateList.initializeStateList(lockerList);
    }

    @Override
    public void updateStateList(LockerList lockerList) {
        stateList.commit();
        stateList.updateStateList(lockerList);
    }

    @Override
    public LockerList undoStateList() throws DukeException {
        return stateList.undo();
    }

    @Override
    public LockerList redoStateList() throws DukeException {
        return stateList.redo();
    }

    @Override
    public void updateHistoryList(String cmd) {
        if (!cmd.equalsIgnoreCase("history")) {
            historyList.updateHistoryList(cmd);
        }
    }

    @Override
    public ArrayList<String> getHistoryList() {
        return historyList.getHistoryList();
    }
}
