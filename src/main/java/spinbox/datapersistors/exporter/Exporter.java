package spinbox.datapersistors.exporter;

import spinbox.DateTime;
import spinbox.datapersistors.FileDataWriter;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Exporter extends FileDataWriter {
    private static final Logger LOGGER = Logger.getLogger(Exporter.class.getName());
    private static final String TIMESTAMP = " as of ";
    private static final String LOG_TITLE_FILEPATH = "Title of file and filepath: ";
    private static final String LOG_WRITE = "Writing to : ";
    private static final String LOG_ERROR_IO_READ_WRITE = "Error R/W file, storage error propagated "
            + "upwards. Filepath: ";
    private static final String EXPORT_TIME = "Exported at timestamp: ";


    private DateTime currentTime = new DateTime(new Date());
    private String titleLine;

    /**
     * A constructor for an instance of the exporter class, which creates the parent folder and/or file as needed.
     * @param fileLocation The path of the file to be exported to.
     * @param titleLine The human-understandable title/header for the exported file.
     * @throws FileCreationException An exception is thrown for file creation errors.
     */
    public Exporter(String fileLocation, String titleLine) throws FileCreationException {
        super(fileLocation);
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "Constructor");
        this.titleLine = titleLine;
        LOGGER.fine(LOG_TITLE_FILEPATH + titleLine + ", " + fileLocation);
        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    /**
     * Saves the provided exportable items into a human-readable snapshot text file.
     * @param exportables A list of items that implement the Exportable interface.
     * @throws DataReadWriteException An exception is thrown for file creation errors.
     */
    @Override
    public void writeData(List<String> exportables) throws DataReadWriteException {
        LOGGER.entering(getClass().getName(), "writeData");
        assert spinBoxFile.exists();
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(spinBoxFile));
            writeTitle(outputStream);

            for (int lineNumber = 0; lineNumber < exportables.size(); lineNumber++) {
                writeExportChunk(lineNumber, exportables.get(lineNumber), outputStream);
            }
            outputStream.close();
        } catch (IOException e) {
            LOGGER.warning(LOG_ERROR_IO_READ_WRITE + spinBoxFile.getPath());
            throw new DataReadWriteException();
        }
        LOGGER.exiting(getClass().getName(), "writeData");
    }

    private void writeTitle(BufferedWriter outputStream) throws IOException {
        LOGGER.entering(getClass().getName(), "writeTitle");
        outputStream.write(titleLine);
        outputStream.write(TIMESTAMP);
        String currentTimeString = currentTime.toString();
        LOGGER.info(EXPORT_TIME + TIMESTAMP + currentTimeString + ", " + spinBoxFile.getPath());
        outputStream.write(currentTimeString);
        outputStream.newLine();
        outputStream.newLine();
        LOGGER.exiting(getClass().getName(), "writeTitle");
    }

    private void writeExportChunk(int lineNumber, String exportString, BufferedWriter outputStream)
            throws IOException {
        LOGGER.entering(getClass().getName(), "writeExportChunk");
        LOGGER.fine(LOG_WRITE + spinBoxFile.getPath() + " : " + exportString);
        outputStream.write(Integer.toString(lineNumber + 1));
        outputStream.write(". ");
        outputStream.write(exportString);
        outputStream.newLine();
        LOGGER.exiting(getClass().getName(), "writeExportChunk");
    }
}
