package duke.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import duke.commons.exceptions.DukeException;
import duke.commons.file.LocalDateAdapter;
import duke.logic.autocorrect.Autocorrect;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author Fractalisk
/**
 * Storage is a public class, a storage class encapsulates the filePath to read from disk and write to disk.
 */
public class Storage {
    private Load loader;
    private Write writer;

    public Storage() {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        loader = new Load(gson);
        writer = new Write(gson);
    }

    /**
     * This is a function that will load all info required to initialize a MealList object.
     */
    public void loadMealInfo(MealList meals) throws DukeException {
        loader.loadMealListData(meals);
        loader.loadDefaultMealData(meals);
    }

    /**
     * This is a function that will load user info from user.txt.
     */
    public User loadUser() throws DukeException {
        User user = loader.loadUser();
        loader.loadGoals(user);
        return user;
    }

    /**
     * This is a function that will load all the words to be autocorrected to.
     */
    public void loadWord(Autocorrect autocorrect) throws DukeException {
        loader.loadAutoCorrect(autocorrect);
    }

    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws DukeException {
        loader.loadHelp(lines, specifiedHelp);
    }

    public void loadTransactions(Wallet wallet) throws DukeException {
        loader.loadTransactions(wallet);
    }

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param mealData the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(MealList mealData) throws DukeException {
        writer.writeFile(mealData);
    }

    /**
     * This is a function that will write data from a MealList object to the defaultitems save file.
     */
    public void updateDefaults(MealList mealData) throws DukeException {
        writer.writeDefaults(mealData);
    }

    /**
     * This is a function that will write data from a MealList object to the goals save file.
     */
    public void updateGoal(User user) throws DukeException {
        writer.writeGoal(user);
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */
    public void updateUser(User user) throws DukeException {
        writer.writeUser(user);
    }

    public void updateTransaction(Wallet wallet) throws DukeException {
        writer.writeTransaction(wallet);
    }
}
