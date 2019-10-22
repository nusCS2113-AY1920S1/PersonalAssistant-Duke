package javacake.storage;

import javacake.exceptions.DukeException;

public class StorageManager {
    public Storage storage;
    public Profile profile;

    public StorageManager() throws DukeException {
        this.storage = new Storage();
        this.profile = new Profile();
    }
}
