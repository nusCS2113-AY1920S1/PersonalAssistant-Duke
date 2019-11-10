package spinbox.datapersistors.storage;

import spinbox.datapersistors.FileDataWriter;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage extends FileDataWriter {
    private static final Logger LOGGER = Logger.getLogger(Storage.class.getName());
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
        super(fileLocation);
        LOGGER.setUseParentHandlers(true);
        LOGGER.setLevel(Level.WARNING);
        LOGGER.entering(getClass().getName(), "Constructor");
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
    @Override
    public void writeData(List<String> lines) throws DataReadWriteException {
        LOGGER.entering(getClass().getName(), "saveData");
        assert spinBoxFile.exists();
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(spinBoxFile));
            for (String line : lines) {
                writeStorageLine(line, outputStream);
            }
            outputStream.close();
        } catch (IOException e) {
            LOGGER.warning(LOG_ERROR_IO_READ_WRITE + spinBoxFile.getPath());
            throw new DataReadWriteException();
        }
        LOGGER.exiting(getClass().getName(), "saveData");
    }

    private void writeStorageLine(String line, BufferedWriter outputStream) throws IOException {
        LOGGER.entering(getClass().getName(), "writeStorageLine");
        LOGGER.fine(LOG_WRITE + spinBoxFile.getPath() + " : " + line);
        outputStream.write(line);
        outputStream.newLine();
        LOGGER.exiting(getClass().getName(), "writeStorageLine");
    }
}
