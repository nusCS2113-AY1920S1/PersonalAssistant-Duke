package spinbox.datapersistors.storage;

import spinbox.exceptions.CorruptedDataException;

public interface Storable {
    String storeString();

    void fromStoredString(String fromStorage) throws CorruptedDataException;
}
