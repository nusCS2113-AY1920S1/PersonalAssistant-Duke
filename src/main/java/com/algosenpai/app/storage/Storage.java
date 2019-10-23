package com.algosenpai.app.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    /**
     * Create storage to IO.
     */
    public Storage() {
    }

    /**
     * Load data from file.
     * @param filePath path to file.
     * @return arraylist of string of data.
     * @throws FileNotFoundException file not found
     */
    public static ArrayList<String> loadData(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scan = new Scanner(file);
        ArrayList<String> inputList = new ArrayList<String>();
        while (scan.hasNextLine()) {
            String taskDetails = scan.nextLine();
            inputList.add(taskDetails);
        }
        return inputList;
    }

    /**
     * Save data into file.
     * @param filePath path to file.
     * @param data data in string.
     * @throws IOException IO error.
     */
    public static void saveData(String filePath, String data) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(data);
        fw.close();
    }

}
