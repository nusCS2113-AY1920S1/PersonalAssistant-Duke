package duke.storage;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeTitleList;
import duke.task.recipetasks.RecipeTitle;

import java.io.*;
import java.util.ArrayList;

/**
 * Handles the ability to read and write to the storage location.
 */
public class RecipeTitleStorage {

    private final ArrayList<RecipeTitle> arrRecipeTitleList = new ArrayList<>();
    private final String filePathRecipeTitle;

    /**
     * Constructor for the class Storage.
     *
     * @param filePathRecipeTitle String containing the directory in which the tasks are to be stored
     */
    public RecipeTitleStorage(String filePathRecipeTitle) {
        this.filePathRecipeTitle = filePathRecipeTitle;
    }

    /**

     * Writing to file to save the task to file.
     *
     * @param recipeTitleList contains the task list
     */
    public void saveFile(RecipeTitleList recipeTitleList) {
        try {
            FileWriter fileWriter = new FileWriter(filePathRecipeTitle);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (RecipeTitle recipeTitle : recipeTitleList.getRecipeTitleList()) {
                bufferedWriter.write(recipeTitle.toSaveString() + "\n");
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
    public ArrayList<RecipeTitle> load() throws DukeException {
        try {
            FileReader fileReader = new FileReader(filePathRecipeTitle);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = "";
            while ((content = bufferedReader.readLine()) != null) {
                String[] split = content.split(" \\| ", 2);
                int index = Integer.parseInt(split[0]);
                if (split.length == 2) {
                    RecipeTitle recipeTitle = new RecipeTitle(index, split[1]);
                    arrRecipeTitleList.add(recipeTitle);
                }
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathRecipeTitle + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathRecipeTitle + "'");
        }
        return arrRecipeTitleList;
    }
}
