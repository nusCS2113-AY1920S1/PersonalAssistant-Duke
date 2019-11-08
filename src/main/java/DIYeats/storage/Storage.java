package DIYeats.storage;

import DIYeats.commons.exceptions.DukeException;
import DIYeats.commons.file.LocalDateAdapter;
import DIYeats.logic.autocorrect.Autocorrect;
import DIYeats.model.meal.MealList;
import DIYeats.model.user.User;
import DIYeats.model.wallet.Wallet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author Fractalisk
/**
 * Storage is a public class, a storage class encapsulates the filePath to read from disk and write to disk.
 */
public class Storage {
    private Load loader;
    private Write writer;
    private int stage = 0;
    private boolean mealIsDone = false;

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
        switch (stage) {
            case 0:
                stage++;
                mealIsDone = false;
                loader.loadMealListData(meals);
                break;
            case 1:
                stage++;
                loader.loadDefaultMealData(meals);
                break;
            case 2:
                mealIsDone = true;
                loader.loadExercises(meals);
                break;
            default:
                mealIsDone = true;
                throw new DukeException("The storage function has entered an invalid state");
        }
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
     * @param meals the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(MealList meals) throws DukeException {
        writer.writeFile(meals);
    }

    public void updateExercises(MealList meals) throws DukeException {
        writer.writeExercises(meals);
    }

    /**
     * This is a function that will write data from a MealList object to the defaultitems save file.
     */
    public void updateDefaults(MealList meals) throws DukeException {
        writer.writeDefaults(meals);
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

    public boolean getMealIsDone() {
        return this.mealIsDone;
    }
}
