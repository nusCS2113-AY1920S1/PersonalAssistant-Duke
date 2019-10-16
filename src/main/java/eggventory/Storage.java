package eggventory;

import eggventory.commands.AddCommand;
import eggventory.enums.CommandType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Handles reading and writing the stockList to file.
 */

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Converts save file details into a StockList object.
     */
    public StockList load() {
        StockList savedList = new StockList();

        if (Files.notExists(Paths.get(filePath))) {
            try {
                Files.createDirectory(Paths.get("data/"));
            } catch (IOException e) {
                System.out.println("Unknown IO error when creating 'data/' folder.");
            }
        }
        File f = new File(filePath);

        try {
            Scanner s = new Scanner(f); //Create a Scanner using the File as the source

            while (s.hasNext()) {
                String itemRaw = s.nextLine();
                String[] item = itemRaw.split("/", 0);

                AddCommand cmd = new AddCommand(CommandType.ADD, item[0], item[1],
                        Integer.parseInt(item[2]), item[3]);

                /*Todo: In the future, call setMinimum here to update the minimum value (item[4]) instead of defaulting.
                    Also applies for other optional params that we may add.
                */
                cmd.execute(savedList);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        //} catch (BadInputException e) {
            //System.out.println("Save file format wrong. Please fix it manually or use a new list.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }

        return savedList; //Returns a StockList.
    }

    private void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Saves existing StockType to a text file.
     */
    public void save(StockList stockList) {
        /*
            StringBuilder tasksToSave = new StringBuilder();

            int max = stockList.getQuantity(); //The number of stockTypes in the stockList.

            for (int i = 0; i < max; i++) { //Index starts from 0.
                tasksToSave.append(stockList.saveDetailsString()).append(System.lineSeparator());
            }
        */
        String taskListToSave = stockList.saveDetailsString();

        try {
            writeToFile(taskListToSave);
        } catch (IOException e) {
            System.out.println("Something went wrong saving the file :(");
        }
    }
}
