package compal.storage;

/**
 * Interface for parsing storage data.
 *
 * @author jaedonkey
 */
public interface StorageParser<T> {
    T parseData(String[] parts);

}
