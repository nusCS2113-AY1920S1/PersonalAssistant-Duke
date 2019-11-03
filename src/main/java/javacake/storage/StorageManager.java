package javacake.storage;

import javacake.exceptions.CakeException;

public class StorageManager {
    public Storage storage;
    public Profile profile;

    public StorageManager() throws CakeException {
        this.storage = new Storage();
        this.profile = new Profile();
    }

    public StorageManager(String filepath) throws CakeException {
        this.storage = new Storage(filepath);
        this.profile = new Profile(filepath);
    }
}
