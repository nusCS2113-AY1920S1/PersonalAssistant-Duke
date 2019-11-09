package diyeats.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import diyeats.commons.exceptions.ProgramException;
import diyeats.commons.file.LocalDateAdapter;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;

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
    public void loadMealInfo(MealList meals) throws ProgramException {
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
                throw new ProgramException("The storage function has entered an invalid state");
        }
    }

    /**
     * This is a function that will load user info from user.txt.
     */
    public User loadUser() throws ProgramException {
        User user = loader.loadUser();
        loader.loadGoals(user);
        return user;
    }

    /**
     * This is a function that will load all the words to be autocorrected to.
     */
    public void loadWord(Autocorrect autocorrect) throws ProgramException {
        loader.loadAutoCorrect(autocorrect);
    }

    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws ProgramException {
        loader.loadHelp(lines, specifiedHelp);
    }

    public void loadTransactions(Wallet wallet) throws ProgramException {
        loader.loadTransactions(wallet);
    }

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param meals the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(MealList meals) throws ProgramException {
        writer.writeFile(meals);
    }

    public void updateExercises(MealList meals) throws ProgramException {
        writer.writeExercises(meals);
    }

    /**
     * This is a function that will write data from a MealList object to the defaultitems save file.
     */
    public void updateDefaults(MealList meals) throws ProgramException {
        writer.writeDefaults(meals);
    }

    /**
     * This is a function that will write data from a MealList object to the goals save file.
     */
    public void updateGoal(User user) throws ProgramException {
        writer.writeGoal(user);
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */
    public void updateUser(User user) throws ProgramException {
        writer.writeUser(user);
    }

    public void updateTransaction(Wallet wallet) throws ProgramException {
        writer.writeTransaction(wallet);
    }

    public boolean getMealIsDone() {
        return this.mealIsDone;
    }
}
