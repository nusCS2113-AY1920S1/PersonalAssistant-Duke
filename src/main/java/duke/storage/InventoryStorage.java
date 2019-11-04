package duke.storage;

import duke.model.list.inventorylist.InventoryList;
import duke.model.task.ingredienttasks.Ingredient;

import java.io.*;
import java.util.HashMap;

/**
 * Handles the ability to read and write to the storage location.
 */
public class InventoryStorage {

    private static final HashMap<String, Ingredient> inventoryListHM = new HashMap<>();
    private final String filePathInventory;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathInventory String containing the directory in which the tasks are to be stored
     */
    public InventoryStorage(String filePathInventory) {
        this.filePathInventory = filePathInventory;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param inventoryList contains the task list
     */
    public void saveFile(InventoryList inventoryList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathInventory);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            inventoryList.getInventoryList().forEach((String, Ingredient) ->
            {
                try {
                    bufferedWriter.write(Ingredient.toSaveString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.close();
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Load all the save tasks in the file.
     *
     * @return the list of tasks in taskList
     */
    public HashMap<String, Ingredient> load() {
            try {
            FileReader fileReader = new FileReader(filePathInventory);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                String ingredientName, quantity, unit, additionalInfo, remaining, remaining2;
                String[] split = content.split("\\|", 2);
                if (split.length == 2) {
                    ingredientName = split[0].trim();
                    remaining = split[1].trim();
                    String[] split2 = remaining.split("\\|", 2);
                    if (split2.length == 2) {
                        quantity = split2[0].trim();
                        remaining2 = split2[1].trim();
                        String[] split3 = remaining2.split("\\|", 2);
                        if (split3.length == 2) {
                            unit = split3[0].trim();
                            additionalInfo = split3[1].trim();
                            Ingredient ingredient = new Ingredient(ingredientName, quantity, unit, additionalInfo);
                            inventoryListHM.put(ingredientName, ingredient);
                        }
                    }
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathInventory + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathInventory + "'");
        }
        return inventoryListHM;
    }
}
