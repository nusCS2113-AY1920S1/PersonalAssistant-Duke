package eggventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import eggventory.exceptions.BadInputException;
import eggventory.commands.AddCommand;
import eggventory.enums.CommandType;
import eggventory.items.Stock;

/**
 * Handles reading and writing the tasklist to file.
 */

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Converts save file details into Stocks.
     */
    public StockType load() {

        StockType savedList = new StockType();
        File f = new File(filePath); //Create a File for the given file path

        try {
            Scanner s = new Scanner(f); //Create a Scanner using the File as the source

            while (s.hasNext()) {
                String itemRaw = s.nextLine();
                String[] item = itemRaw.split("/", 0);

                AddCommand cmd = new AddCommand(CommandType.ADD, item[0], item[1],
                        Integer.parseInt(item[2]), item[3]);
                cmd.execute(savedList);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found. New list will be created instead.");
        //} catch (BadInputException e) {
            //System.out.println("Save file format wrong. Please fix it manually or use a new list.");
        } catch (Exception e) {
            System.out.println("Save file cannot be read. Please fix it manually or use a new list.");
        }

        return savedList; //Returns a StockType.
    }

    private void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    /**
     * Saves existing StockType to a text file.
     */
    public void save(ArrayList<Stock> stockList) {
        StringBuilder tasksToSave = new StringBuilder();
        int max = stockList.size();
        for (int i = 0; i < max; i++) { //index starts from 0.
            tasksToSave.append(stockList.get(i).saveDetailsString()).append(System.lineSeparator());
        }

        String taskListToSave = tasksToSave.toString();
        try {
            writeToFile(taskListToSave);
        } catch (IOException e) {
            System.out.println("Something went wrong saving the file :(");
        }
    }
}
