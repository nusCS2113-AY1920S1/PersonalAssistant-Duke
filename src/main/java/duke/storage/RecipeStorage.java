package duke.storage;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.list.recipelist.RecipeList;
import duke.model.recipe.Recipe;
import duke.task.ingredienttasks.Ingredient;
import duke.list.ingredientlist.IngredientList;
import duke.task.recipetasks.RecipeIngredient;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles the ability to read and write to the storage location.
 */
public class RecipeStorage {

    private final Map<String, Recipe> arrRecipeMap = new HashMap<>();
    private final String filePathRecipe;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathRecipe String containing the directory in which the tasks are to be stored
     */
    public RecipeStorage(String filePathRecipe) {
        this.filePathRecipe = filePathRecipe;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param recipeList contains the task list
     */
    public void saveFile(String recipeTitle, RecipeList recipeList) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePathRecipe);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recipeList);
            fileOutputStream.close();
            objectOutputStream.close();
//            FileWriter fileWriter = new FileWriter(filePathRecipe);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.
//            for (Object recipe : recipeList.getValue()) {
//                bufferedWriter.write(recipeTitle + recipe + "\n");
//            }
//            bufferedWriter.close();
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
    public Map<String, Recipe> load() throws DukeException {
        Map<String, Recipe> temp = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePathRecipe));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            temp = (Map<String, Recipe>) objectInputStream.readObject();
//            FileReader fileReader = new FileReader(filePathRecipe);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String content = "";
//            while ((content = bufferedReader.readLine()) != null) {
//                String[] split = content.split(" \\| ", 2);
//                if (split.length == 2) {
//                    int quantity = Integer.parseInt(split[1]);
//                    Recipe recipe = new Recipe(split[0], quantity);
//                    arrRecipeMap.put(recipe);
//                }
//            }
//            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathRecipe + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathRecipe + "'");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
