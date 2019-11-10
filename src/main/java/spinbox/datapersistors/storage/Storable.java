package spinbox.datapersistors.storage;

import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.DateFormatException;

public interface Storable {
    String storeString();

    void fromStoredString(String fromStorage) throws CorruptedDataException, DateFormatException;
}
