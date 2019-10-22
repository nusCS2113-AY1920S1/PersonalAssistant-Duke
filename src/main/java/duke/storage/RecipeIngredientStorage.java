package duke.storage;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
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
                String recipeIndex, recipeIngredientName, quantity, unit, additionalInfo, remaining, remaining1, remaining2;
                String[] split = content.split(" \\| ", 2);
                if (split.length == 2) {
                    recipeIndex = split[0].trim();
                    remaining = split[1].trim();
                    String[] split1 = remaining.split("\\|", 2);
                    if (split1.length == 2) {
                        recipeIngredientName = split1[0].trim();
                        remaining1 = split1[1].trim();
                        String[] split2 = remaining1.split("\\|", 2);
                        if (split2.length == 2) {
                            quantity = split2[0].trim();
                            remaining2 = split2[1].trim();
                            String[] split3 = remaining2.split("\\|", 2);
                            if (split3.length == 2) {
                                unit = split3[0].trim();
                                additionalInfo = split3[1].trim();
                                System.out.println(recipeIndex + " " + recipeIngredientName + " " + quantity + " " + unit + " " + additionalInfo);
                                RecipeIngredient ingredient = new RecipeIngredient(recipeIndex, recipeIngredientName, quantity, unit, additionalInfo);
                                arrRecipeIngredientList.add(ingredient);
                            }
                        }
                    }
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
