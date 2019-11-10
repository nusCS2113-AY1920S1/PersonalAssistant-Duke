package com.algosenpai.app.storage;

import com.algosenpai.app.stats.UserStats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Storage {

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
            return Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
        } catch (IOException e) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
