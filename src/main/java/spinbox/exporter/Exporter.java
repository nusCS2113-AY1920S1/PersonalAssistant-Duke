package spinbox.exporter;

import spinbox.DateTime;
import spinbox.entities.items.Item;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Exporter {
    private static final String TIMESTAMP = " as of ";

    private DateTime currentTime = new DateTime(new Date());
    private java.io.File spinBoxFile;
    private String titleLine;

    /**
     * A constructor for an instance of the exporter class, which creates the parent folder and/or file as needed.
     * @param fileLocation The path of the file to be exported to.
     * @param titleLine The human-understandable title/header for the exported file.
     * @throws FileCreationException An exception is thrown for file creation errors.
     */
    public Exporter(String fileLocation, String titleLine) throws FileCreationException {
        try {
            this.titleLine = titleLine;
            spinBoxFile = new File(fileLocation);
            spinBoxFile.getParentFile().mkdir();
            spinBoxFile.createNewFile();
        } catch (IOException e) {
            throw new FileCreationException(e.getMessage());
        }
        assert spinBoxFile.exists();
        assert spinBoxFile.isFile();
    }

    /**
     * Saves the provided exportable items into a human-readable snapshot text file.
     * @param exportables A list of items that implement the Exportable interface.
     * @throws DataReadWriteException An exception is thrown for file creation errors.
     */
    public void writeData(List<? extends Item> exportables) throws DataReadWriteException {
        assert spinBoxFile.exists();
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(spinBoxFile));
            outputStream.write(titleLine);
            outputStream.write(TIMESTAMP);
            outputStream.write(currentTime.toString());
            outputStream.newLine();
            outputStream.newLine();

            for (int lineNumber = 0; lineNumber < exportables.size(); lineNumber++) {
                outputStream.write(Integer.toString(lineNumber + 1));
                outputStream.write(". ");
                outputStream.write(exportables.get(lineNumber).exportString());
                outputStream.newLine();
            }
            outputStream.close();
        } catch (IOException e) {
            throw new DataReadWriteException();
        }
    }
}
