package duke.storage;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeList;
import duke.task.recipetasks.Recipe;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * Handles the ability to read and write to the storage location.
 */
public class RecipeStorage {

    private final LinkedHashMap<Integer, Recipe> LHMRecipeList = new LinkedHashMap<>();
    private final String filePathRecipes;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathRecipes String containing the directory in which the tasks are to be stored
     */
    public RecipeStorage(String filePathRecipes) {
        this.filePathRecipes = filePathRecipes;
    }

    /**
     * Writing to file to save the task to file.
     *
     * @param recipeList contains the task list
     */
    public void saveFile(RecipeList recipeList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathRecipes);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            recipeList.getRecipeList().forEach((Integer, Recipe) ->
            {
                try {
                    bufferedWriter.write(recipeList.getRecipeIndex() + " | " + Recipe.toSaveString() + "\n");
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
     * @throws DukeException if Duke is not able to load the tasks from the file or unable to open the file
     */
    public LinkedHashMap<Integer, Recipe> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePathRecipes);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                // can use a splitMethod() here for tidyness?
                String recipeIndex, recipeTitle, rating, prepStep, requiredIngredients, feedback, remaining, remaining1, remaining2, remaining3;
                String[] split = content.split("\\|", 2);
                if (split.length == 2) {
                    recipeIndex = content.split("\\|", 2)[0].trim();
                    remaining = content.split("\\|", 2)[1].trim();
                    String[] split1 = remaining.split("\\|", 2);
                    if (split1.length == 2) {
                        recipeTitle = remaining.split("\\|", 2)[0].trim();
                        remaining1 = remaining.split("\\|", 2)[1].trim();
                        String[] split2 = remaining1.split("\\|", 2);
                        if (split2.length == 2) {
                            rating = remaining1.split("\\|", 2)[0].trim();
                            remaining2 = remaining1.split("\\|", 2)[1].trim();
                            String[] split3 = remaining2.split("\\|", 2);
                            if (split3.length == 2) {
                                prepStep = remaining2.split("\\|", 2)[0].trim();
                                remaining3 = remaining2.split("\\|", 2)[1].trim();
                                String[] split4 = remaining3.split("\\|", 2);
                                if (split4.length == 2) {
                                    requiredIngredients = remaining3.split("\\|", 2)[0].trim();
                                    feedback = remaining3.split("\\|", 2)[1].trim();
                                    Recipe recipe = new Recipe(recipeTitle, rating, prepStep, requiredIngredients, feedback);
                                    LHMRecipeList.put(Integer.parseInt(recipeIndex), recipe);
                                }
                            }
                        }
                    }
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathRecipes + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathRecipes + "'");
        }
        return LHMRecipeList;
    }
}