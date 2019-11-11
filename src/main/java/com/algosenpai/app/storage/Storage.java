package com.algosenpai.app.storage;

import com.algosenpai.app.MainApp;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.utility.LogCenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Storage {

    private static final Logger logger = LogCenter.getLogger(Storage.class);

    /**
     * Load data from file.
     * @param filePath path to file.
     * @return A string containing the exact text in the file, including newlines.
     */
    public static String loadData(String filePath) throws FileNotFoundException {
        File file = new File(filePath);

        if (!file.isFile()) {
            throw new FileNotFoundException();
        }

        try {
            logger.info("Loading data from file specified..");
            return Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            logger.info("Data not loaded successfully due to IO error.");
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Save data into file.
     * @param filePath path to file.
     * @param data data in string.
     */
    public static void saveData(String filePath, String data) {
        try {
            if (!new File(filePath).isFile()) {
                new File(filePath).createNewFile();
            }

            FileWriter fw = new FileWriter(filePath);
            fw.write(data);
            fw.close();
            logger.info("Successfully saved data to file.");
        } catch (IOException e) {
            logger.info("Data not saved to file due to IO error.");
            e.printStackTrace();
        }
    }



}
