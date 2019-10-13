package duke.storage;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.task.ingredienttasks.Ingredient;
import duke.list.ingredientlist.IngredientList;
import duke.task.recipetasks.RecipeIngredient;

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
public class RecipeIngredientStorage {

    private static final ArrayList<RecipeIngredient> arrRecipeIngredientList = new ArrayList<>();
    private final String filePathRecipeIngredients;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathRecipeIngredients String containing the directory in which the tasks are to be stored
     */
    public RecipeIngredientStorage(String filePathRecipeIngredients) {
        this.filePathRecipeIngredients = filePathRecipeIngredients;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param recipeIngredientList contains the task list
     */
    public void saveFile(RecipeIngredientList recipeIngredientList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathRecipeIngredients);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (RecipeIngredient recipeIngredient : recipeIngredientList.getRecipeIngredientList()) {
                bufferedWriter.write(recipeIngredient.toSaveString() + "\n");
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
    public ArrayList<RecipeIngredient> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePathRecipeIngredients);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                String[] split = content.split(" \\| ", 4);
                if (split.length == 4) {
                    int index = Integer.parseInt(split[0]);
                    double quantity = Double.parseDouble(split[2]);
                    RecipeIngredient recipeIngredient = new RecipeIngredient(index, split[1], quantity, split[3]);
                    arrRecipeIngredientList.add(recipeIngredient);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathRecipeIngredients + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathRecipeIngredients + "'");
        }
        return arrRecipeIngredientList;
    }
}
