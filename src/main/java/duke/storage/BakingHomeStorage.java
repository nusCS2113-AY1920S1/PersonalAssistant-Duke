package duke.storage;

import duke.logic.command.exceptions.DataConversionException;
import duke.model.ReadOnlyBakingHome;

import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link duke.model.BakingHome}.
 */
public interface BakingHomeStorage {

    /**
     * Returns the file path of the data file.
     * @return
     */
    java.nio.file.Path getBakingHomeFilePath();

    /**
     * Returns BakingHome data as a {@link ReadOnlyBakingHome}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBakingHome> readBakingHome() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBakingHome} to the storage.
     * @param bakingHome cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBakingHome(ReadOnlyBakingHome bakingHome) throws IOException;

}
