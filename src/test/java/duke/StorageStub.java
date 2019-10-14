package duke;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;

public class StorageStub extends Storage {

    public StorageStub() throws DukeException {
        super("tasks.txt");
    }

    @Override
    protected void read() {
    }
}
