package duke.data;

import duke.models.TimeSlot;

import java.io.IOException;

/**
 * Interface for loading classes.
 */
public interface IStorage {
    /**
     * Method will load saved files.
     */
    public void load() throws IOException;

    /**
     * Method will save file to location.
     */
    public void save(TimeSlot timeSlot) throws IOException;
}
