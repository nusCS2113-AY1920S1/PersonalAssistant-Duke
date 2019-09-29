package javacake;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Provides the template for an object that reads in text content from file.
 */
public abstract class ContentFormat {
    String name;
    String filePath;

    private String getContent() throws DukeException {
        StringBuilder toDisplay = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new DukeException("File does not exist!");
        }
        try {
            while (reader.readLine() != null) {
                toDisplay.append(reader.readLine()).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            throw new DukeException("Error reading from file!");
        }
        return toDisplay.toString();
    }

    public String getName() {
        return name;
    }
}
