package duke.storage;

import duke.core.DukeException;

import java.util.ArrayList;

/**
 * Represents a Storage class that deals with reading tasks from
 * a file and saving tasks in the file.
 */
public abstract class Storage<T> {

    public abstract ArrayList<T> load() throws DukeException;

    public abstract void save(ArrayList<T> list) throws DukeException;

}
