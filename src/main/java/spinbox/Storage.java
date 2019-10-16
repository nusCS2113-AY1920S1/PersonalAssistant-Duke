package spinbox;

import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.StorageException;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private java.io.File spinBoxFile;

    /**
     * This constructor takes in a path, creating the file and/or folder as needed.
     * @param fileLocation relative path of the text file to store data in.
     * @throws StorageException An exception is thrown for file creation errors.
     */
    public Storage(String fileLocation) throws FileCreationException {
        try {
            spinBoxFile = new File(fileLocation);
            if (spinBoxFile.getParentFile().mkdir()) {
                spinBoxFile.createNewFile();
            }
        } catch (IOException e) {
            throw new FileCreationException(e.getMessage());
        }
    }

    /**
     * Retrieves lines of text from within the text file.
     * @return A list containing Strings of data retrieved from the text file.
     * @throws SpinBoxException An exception is thrown for I/O errors.
     */
    public List<String> loadData() throws StorageException {
        ArrayList<String> lines = new ArrayList<>();
        try {
            String currentLine;
            BufferedReader inputStream = new BufferedReader(new FileReader(spinBoxFile));
            while ((currentLine = inputStream.readLine()) != null) {
                lines.add(currentLine);
            }
            inputStream.close();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
        return lines;
    }

    /**
     * Saves lines of text to a text file at the path specified.
     * @param lines List of Strings to be saved line by line to the text file.
     * @throws StorageException An exception is thrown for I/O errors.
     */
    public void saveData(List<String> lines) throws StorageException {
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(spinBoxFile));
            for (String line : lines) {
                outputStream.write(line);
                outputStream.newLine();
            }
            outputStream.close();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
