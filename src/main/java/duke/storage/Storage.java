package duke.storage;

import java.util.ArrayList;

import duke.commons.exceptions.DukeException;
import duke.model.*;
import duke.model.user.User;
import duke.logic.autocorrect.Autocorrect;

import static duke.commons.FilePaths.*;

/**
 * Storage is a public class, a storage class encapsulates the filePath to read from disk and write to disk.
 * @author Ivan Andika Lie
 */
public class Storage {
    private Load loader = new Load();
    private Write writer = new Write();

    public void load(MealList meals) throws DukeException {
        loader.loadFile(meals, DATA_FILE);
        loader.loadFile(meals, DEFAULTS_FILE);
        loader.loadFile(meals, GOAL_FILE);
    }

    /**
     * This is a function that will load user info from user.txt.
     * @Author Foo Chi Hen
     */
    public User loadUser() throws DukeException {
        return loader.loadUser();
    }

    /**
     * This is a function that will load all the words to be autocorrected to.
     * @Author Foo Chi Hen
     */
    public void loadWord(Autocorrect autocorrect) throws DukeException {
        loader.loadAutoCorrect(autocorrect);
    }

    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws DukeException {
        loader.loadHelp(lines, specifiedHelp);
    }

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param mealData the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(MealList mealData) {
        writer.writeFile(mealData);
    }

    public void updateDefaults(MealList mealData) {
        writer.writeDefaults(mealData);
    }

    public void updateGoal(MealList mealData) {
        writer.writeGoal(mealData);
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     * @Author Foo Chi Hen
     */
    public void saveUser(User user) throws DukeException {
        writer.writeUser(user);
    }
}
