package spinbox.datapersistors.storage;

import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.DateFormatException;
import spinbox.exceptions.FileCreationException;

public interface StorageContainer {
    void saveData() throws DataReadWriteException;

    void loadData() throws DataReadWriteException, CorruptedDataException, FileCreationException, DateFormatException;
}

