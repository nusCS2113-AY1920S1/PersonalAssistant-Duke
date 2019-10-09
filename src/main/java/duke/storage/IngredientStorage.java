package duke.storage;

import duke.exception.DukeException;
import duke.task.ingredienttasks.Ingredient;
import duke.ingredientlist.IngredientList;

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

                /*
                if (content.charAt(0) == 'T') {
                    if (content.charAt(1) == 'S') {
                        String[] split = content.substring(9).split(" \\| ", 2);
                        Task task = new TentativeScheduling(split[0], split[1]);
                        if (content.charAt(5) == '+') {
                            task.markAsDone();
                        }
                        arrTaskList.add(task);
                    } else {
                        String details = content.substring(8);
                        Task task = new Todo(details);
                        if (content.charAt(4) == '+') {
                            task.markAsDone();
                        }
                        arrTaskList.add(task);
                    }
                } else {
                    //need to escape character in string for "|" by adding "\\" in front of "|"
                    //if not the split will be on the wrong place
                    String[] split = content.substring(8).split(" \\| ");
                    if (content.charAt(0) == 'D') {
                        Task task = new Deadline(split[0], split[1]);
                        assignTaskMarker(content, task);
                    } else if (content.charAt(0) == 'E') {
                        Task task = new Event(split[0], split[1]);
                        assignTaskMarker(content, task);
                    } else if (content.charAt(0) == 'F') {
                        Task task = new Duration(split[0], split[1]);
                        assignTaskMarker(content, task);
                    } else if (content.charAt(0) == 'P') {
                        if (split.length == 3) {
                            Task task = new Period(split[0], split[1], split[2]);
                            assignTaskMarker(content, task);
                        }
                    } else if (content.charAt(0) == 'R') {
                        if (split.length == 2) { // daily
                            Task task = new Recurring(split[0], split[1], "");
                            assignTaskMarker(content, task);
                        } else if (split.length == 3) { // weekly, monthly, yearly
                            Task task = new Recurring(split[0], split[1], split[2]);
                            assignTaskMarker(content, task);
                        }
                    }
                }
                */
            }
            fileReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePathIngredients + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + filePathIngredients + "'");
        }
        return arrIngredientList;
    }

    /*
    private static void assignTaskMarker(String content, Task task) {
        if (content.charAt(4) == '+') {
            task.markAsDone();
        }
        arrTaskList.add(task);
    }
    */
}
