package duke.storage;

import duke.model.list.recipelist.RecipeList;
import duke.model.task.recipetasks.Recipe;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

import static duke.common.Messages.filePathRecipesTest;

/**
 * Handles the ability to read and write to the recipe storage location.
 */
public class RecipeStorage {

    private final TreeMap<String, Recipe> LHMRecipeList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final String filePathRecipes;

    /**
     * Constructor for the class RecipeStorage.
     *
     * @param filePathRecipes the directory in which the recipes are to be stored
     */
    public RecipeStorage(String filePathRecipes) {
        this.filePathRecipes = filePathRecipes;
    }

    /**
     * Writes to file to save the recipes to file.
     *
     * @param recipeList the list containing recipes
     */
    public void saveFile(RecipeList recipeList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathRecipes);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            recipeList.getRecipeList().forEach((String, Recipe) ->
            {
                try {
                    bufferedWriter.write(Recipe.toSaveString() + "\n");
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
     * Loads all the save recipes in the file.
     *
     * @return the list of recipes in recipe list
     */
    public TreeMap<String, Recipe> load() {

        if (Files.notExists(Paths.get(filePathRecipes))) {
            try {
                File file = new File(filePathRecipes);
                file.getParentFile().mkdir();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Unknown IO error when creating 'data/' folder.");
            }

//            try {
//                Files.createDirectory(Paths.get("data/"));
//            } catch (IOException e) {
//                System.out.println("Unknown IO error when creating 'data/' folder.");
//            }
        }
        try {
            InputStream inputStream;
            if (filePathRecipes.equals(filePathRecipesTest)) {
                inputStream = getClass().getResourceAsStream("/data/recipesTest.txt");
            } else {
                inputStream = getClass().getResourceAsStream("/data/recipes.txt");
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            FileReader fileReader = new FileReader(filePathRecipes);
            BufferedReader bufferedReader1 = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null || (content = bufferedReader1.readLine()) != null) {
                // can use a splitMethod() here for tidyness?
                String recipeTitle, prepTime, rating, prepSteps, requiredIngredients, feedback, remaining, remaining2, remaining3, remaining4;
                String[] split = content.split("\\|", 2);
                if (split.length == 2) {
                    recipeTitle = split[0].trim();
                    remaining = split[1].trim();
                    String[] split2 = remaining.split("\\|", 2);
                    if (split2.length == 2) {
                        prepTime = split2[0].trim();
                        remaining2 = split2[1].trim();
                        String[] split3 = remaining2.split("\\|", 2);
                        if (split3.length == 2) {
                            rating = split3[0].trim();
                            remaining3 = split3[1].trim();
                            String[] split4 = remaining3.split("\\|", 2);
                            if (split4.length == 2) {
                                prepSteps = split4[0].trim();
                                remaining4 = split4[1].trim();
                                String[] split5 = remaining4.split("\\|", 2);
                                if (split5.length == 2) {
                                    requiredIngredients = split5[0].trim();
                                    feedback = split5[1].trim();
                                    Recipe recipe = new Recipe(recipeTitle, prepTime, rating, prepSteps, requiredIngredients, feedback);
                                    LHMRecipeList.put(recipeTitle, recipe);
                                }
                            }
                        }
                    }
                }
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathRecipes + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathRecipes + "'");
        }
        return LHMRecipeList;
    }
}