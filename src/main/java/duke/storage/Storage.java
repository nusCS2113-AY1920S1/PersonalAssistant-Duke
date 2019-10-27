package duke.storage;

import java.util.ArrayList;

import duke.commons.exceptions.DukeException;
import duke.commons.file.FilePaths;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.logic.autocorrect.Autocorrect;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;

/**
 * Storage is a public class, a storage class encapsulates the filePath to read from disk and write to disk.
 */
public class Storage {
    private Load loader;
    private Write writer;
    private FilePaths filePaths;

    public Storage() {
        loader = new Load();
        writer = new Write();
        filePaths = new FilePaths();
    }

    /**
     * This is a function that will load all info required to initialize a MealList object.
     */
    public void load(MealList meals) throws DukeException {
        loader.loadFile(meals, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_USER_MEALS_FILE));
        loader.loadFile(meals, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE));
        loader.loadFile(meals, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_GOAL_FILE));
    }

    /**
     * This is a function that will load user info from user.txt.
     */
    public User loadUser() throws DukeException {
        return loader.loadUser();
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

    public void loadTransactions(TransactionList transactions, Wallet wallet) throws DukeException {
        loader.loadTransactions(transactions, wallet);
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
    public void updateGoal(MealList mealData) throws DukeException {
        writer.writeGoal(mealData);
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */
    public void saveUser(User user) throws DukeException {
        writer.writeUser(user);
    }

    public void updateTransaction(TransactionList transactionList) throws DukeException {
        writer.writeTransaction(transactionList);
    }
}
