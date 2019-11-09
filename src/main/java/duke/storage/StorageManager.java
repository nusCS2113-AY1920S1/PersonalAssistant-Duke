package duke.storage;


import duke.exceptions.DukeException;
import duke.models.LockerList;

import static java.util.Objects.requireNonNull;

public class StorageManager implements Storage {

    private FileStorage fileStorage;
    private OpenCsv writeToCsv;

    public StorageManager(String fileName) {
        fileStorage = new FileStorage(fileName);
        writeToCsv = new OpenCsv();
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
        writeToCsv.exportLockers(lockerList.getAllLockers());
    }
}
