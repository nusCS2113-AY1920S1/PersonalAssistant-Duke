package duke.storage;

import duke.exception.DukeException;
import duke.task.ingredienttasks.Ingredient;
import duke.list.ingredientlist.IngredientList;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the storage location.
 */
public class IngredientStorage {

    private static final ArrayList<Ingredient> arrIngredientList = new ArrayList<>();
    private final String filePathIngredients;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathIngredients String containing the directory in which the tasks are to be stored
     */
    public IngredientStorage(String filePathIngredients) {
        this.filePathIngredients = filePathIngredients;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param ingredientList contains the task list
     */
    public void saveFile(IngredientList ingredientList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathIngredients);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Ingredient ingredient : ingredientList.getIngredientList()) {
                bufferedWriter.write(ingredient.toSaveString() + "\n");
            }
            bufferedWriter.close();
        } catch (Exception exc) {
            exc.printStackTrace(); // If there was an error, print the info.
        }
    }

    /**
     * Load all the save tasks in the file.
     *
     * @return the list of tasks in taskList
     * @throws DukeException if Duke is not able to load the tasks from the file or unable to open the file
     */
    public ArrayList<Ingredient> load() throws DukeException {
            try {
            FileReader fileReader = new FileReader(filePathIngredients);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                String[] split = content.split(" \\| ", 2);
                if (split.length == 2) {
                    int quantity = Integer.parseInt(split[1]);
                    Ingredient ingredient = new Ingredient(split[0], quantity);
                    arrIngredientList.add(ingredient);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathIngredients + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathIngredients + "'");
        }
        return arrIngredientList;
    }
}
