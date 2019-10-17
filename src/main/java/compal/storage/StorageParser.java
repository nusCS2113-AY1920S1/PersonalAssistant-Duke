package compal.storage;

/**
 * Interface for parsing storage data.
 * @author jaedonkey
 */
public interface StorageParser<T> {
    public T parseData(String[] parts);

}
