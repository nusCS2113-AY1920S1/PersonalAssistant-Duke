package oof.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a Storage class to load and write data from/to persistent storage.
 */
public class Storage {

    private static final Logger LOGGER = Logger.getLogger("OofLogger");

    /**
     * Constructor for Storage class.
     */
    public Storage() {
    }

    /**
     * Loads a file from persistent storage.
     *
     * @param filePath String containing file path name
     * @return ArrayList of String containing data stored in file
     * @throws NullPointerException if file does not exist.
     */
    public ArrayList<String> loadFile(String filePath) throws NullPointerException {
        BufferedReader bufferedReader;
        try {
            FileReader fileReader = new FileReader(filePath);
             bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }
        ArrayList<String> data = new ArrayList<>();
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return data;
    }

    /**
     * Writes to a file from persistent storage.
     *
     * @param filePath String containing file path name
     * @param data     ArrayList of data to be written
     */
    public void writeFile(String filePath, ArrayList<String> data) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            for (String datum : data) {
                bufferedWriter.write(datum + '\n');
            }
            bufferedWriter.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
