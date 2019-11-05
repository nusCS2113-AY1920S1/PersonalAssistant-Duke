package duke.storage;

import duke.commons.core.LogsCenter;
import duke.commons.util.FileUtil;
import duke.commons.util.JsonUtil;
import duke.logic.command.exceptions.DataConversionException;
import duke.logic.command.exceptions.IllegalValueException;
import duke.model.ReadOnlyBakingHome;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access BakingHome data stored as a json file on the hard disk.
 */
public class JsonBakingHomeStorage implements BakingHomeStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBakingHomeStorage.class);

    private Path filePath;

    public JsonBakingHomeStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBakingHomeFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBakingHome> readBakingHome() throws DataConversionException {
        return readBakingHome(filePath);
    }

    /**
     * Similar to {@link #readBakingHome()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBakingHome> readBakingHome(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBakingHome> jsonBakingHome = JsonUtil.readJsonFile(
                filePath, JsonSerializableBakingHome.class);
        if (jsonBakingHome.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBakingHome.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBakingHome(ReadOnlyBakingHome bakingHome) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        saveBakingHome(bakingHome, filePath);
    }

    /**
     * Similar to {@link #saveBakingHome(ReadOnlyBakingHome)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBakingHome(ReadOnlyBakingHome bakingHome, Path filePath) throws IOException {
        requireNonNull(bakingHome);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBakingHome(bakingHome), filePath);
    }

}
