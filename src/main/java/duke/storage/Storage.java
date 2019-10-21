package duke.storage;

import duke.core.DukeException;

import java.util.ArrayList;
import java.util.Map;

public abstract class Storage {

    /**
     * Write info to local csv files.
     *
     * @param infoList A list of records to be to be written in rows to csv file
     * @throws DukeException throw exception with error message when i/o fails
     */
    public abstract void write(ArrayList<ArrayList<String>> infoList, String[] headers) throws DukeException;

    /**
     * Load the patients' info from local csv files.
     *
     * @return A list of rows in Map with header as key and column in row as value
     * @throws DukeException throw a dukeException with error message for debugging
     */
    public abstract ArrayList<Map<String, String>> read() throws DukeException;
}
