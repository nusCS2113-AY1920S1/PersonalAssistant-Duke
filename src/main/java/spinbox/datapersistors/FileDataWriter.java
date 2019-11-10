package spinbox.datapersistors;

import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FileDataWriter {
    protected java.io.File spinBoxFile;
    private static final Logger LOGGER = Logger.getLogger(FileDataWriter.class.getName());
    private static final String LOG_DIRECTORY_FILE_BOOLEAN = "Created:-> directory, file: ";
    private static final String LOG_ERROR_IO_CREATION = "Error creating file/directory, storage error propagated "
            + "upwards. Filepath: ";

    /**
     * This constructor takes in a path, creating the file and/or folder as needed.
     * @param fileLocation relative path of the text file to store data in.
     * @throws FileCreationException An exception is thrown for file creation errors.
     */
    public FileDataWriter(String fileLocation) throws FileCreationException {
        LOGGER.entering(getClass().getName(), "Constructor");
        LOGGER.setLevel(Level.WARNING);
        try {
            spinBoxFile = new File(fileLocation);
            boolean directoryMade = spinBoxFile.getParentFile().mkdir();
            boolean fileCreated = spinBoxFile.createNewFile();
            LOGGER.info(LOG_DIRECTORY_FILE_BOOLEAN + directoryMade + " " + fileCreated + " " + fileLocation);
        } catch (IOException e) {
            LOGGER.warning(LOG_ERROR_IO_CREATION + fileLocation);
            throw new FileCreationException(e.getMessage());
        }
        assert spinBoxFile.exists();
        assert spinBoxFile.isFile();
        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    public abstract void writeData(List<String> items) throws DataReadWriteException;
}
