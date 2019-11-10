package spinbox.storage;

import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {
    private java.io.File spinBoxFile;
    private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());
    private static final String LOG_DIRECTORY_FILE_BOOLEAN = "Created:-> directory, file: ";
    private static final String LOG_ERROR_IO_CREATION = "Error creating file/directory, storage error propagated "
            + "upwards. Filepath: ";
    private static final String LOG_ERROR_IO_READ_WRITE = "Error R/W file, storage error propagated "
            + "upwards. Filepath: ";
    private static final String LOG_READ = "Reading from : ";
    private static final String LOG_WRITE = "Writing to : ";

    /**
     * This constructor takes in a path, creating the file and/or folder as needed.
     * @param fileLocation relative path of the text file to store data in.
     * @throws FileCreationException An exception is thrown for file creation errors.
     */
    public Storage(String fileLocation) throws FileCreationException {
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

    /**
     * Retrieves lines of text from within the text file.
     * @return A list containing Strings of data retrieved from the text file.
     * @throws DataReadWriteException An exception is thrown for I/O errors.
     */
    public List<String> loadData() throws DataReadWriteException {
        LOGGER.entering(getClass().getName(), "loadData");
        assert spinBoxFile.exists();
        ArrayList<String> lines = new ArrayList<>();
        try {
            String currentLine;
            BufferedReader inputStream = new BufferedReader(new FileReader(spinBoxFile));
            while ((currentLine = inputStream.readLine()) != null) {
                LOGGER.fine(LOG_READ + spinBoxFile.getPath() + " : " + currentLine);
                lines.add(currentLine);
            }
            inputStream.close();
        } catch (IOException e) {
            LOGGER.warning(LOG_ERROR_IO_READ_WRITE + spinBoxFile.getPath());
            throw new DataReadWriteException();
        }
        LOGGER.exiting(getClass().getName(), "loadData");
        return lines;
    }

    /**
     * Saves lines of text to a text file at the path specified.
     * @param lines List of Strings to be saved line by line to the text file.
     * @throws DataReadWriteException An exception is thrown for I/O errors.
     */
    public void saveData(List<String> lines) throws DataReadWriteException {
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "saveData");
        assert spinBoxFile.exists();
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(spinBoxFile));
            for (String line : lines) {
                LOGGER.fine(LOG_WRITE + spinBoxFile.getPath() + " : " + line);
                outputStream.write(line);
                outputStream.newLine();
            }
            outputStream.close();
        } catch (IOException e) {
            LOGGER.warning(LOG_ERROR_IO_READ_WRITE + spinBoxFile.getPath());
            throw new DataReadWriteException();
        }
        LOGGER.exiting(getClass().getName(), "saveData");
    }
}
