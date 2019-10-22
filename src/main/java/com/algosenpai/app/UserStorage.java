package com.algosenpai.app;

import com.algosenpai.app.UserStats;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserStorage {

    /**
     * Save the userStats object into permanent storage. The contents of filename will be overwritten.
     * @param filename The name of the file to write into. Please enter a valid filename ending in .txt or bad things
     *                 may happen.
     * @param userStats The UserStats object you want to store.
     * @throws IOException If IO error occurs.
     */
    public static void saveStatsToFile(String filename, UserStats userStats) throws IOException{

        // Create file if it doesn't exist.
        if (! new File(filename).isFile()) {
            new File(filename).createNewFile();
        }

        FileWriter out = null;
        // Overwrites the file.
        out = new FileWriter(filename);
        out.write(userStats.toString());
        out.close();
    }

    /**
     *
     * @param filename
     */
    public static void getStatsFromFile(String filename) throws IOException{
        if (! new File(filename).isFile()) {
//            new File();
        }
    }

}
