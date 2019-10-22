package com.algosenpai.app.storage;

import com.algosenpai.app.stats.UserStats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    /**
     * Load data from file.
     * @param filePath path to file.
     * @return A string containing the exact text in the file, including newlines.
     * @throws FileNotFoundException file not found
     */
    public static String loadData(String filePath) throws IOException {
        File file = new File(filePath);
        // If the file does not exist, create it.
        if (! file.isFile()) {
            file.createNewFile();

            // Fill it with the default UserStats.
            UserStats newUserStats = UserStats.getDefaultUserStats();
            // Save it to the file, so the next time the function is called, valid data exists in the file.
            saveData(filePath,newUserStats.toString());
            return newUserStats.toString();
        }

        return Files.readString(Paths.get(filePath), StandardCharsets.US_ASCII);
    }

    /**
     * Save data into file.
     * @param filePath path to file.
     * @param data data in string.
     * @throws IOException IO error.
     */
    public static void saveData(String filePath, String data) throws IOException {

        if (! new File(filePath).isFile()) {
            new File(filePath).createNewFile();
        }

        FileWriter fw = new FileWriter(filePath);
        fw.write(data);
        fw.close();
    }



}
