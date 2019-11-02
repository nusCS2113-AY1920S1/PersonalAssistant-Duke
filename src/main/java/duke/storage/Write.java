package duke.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import duke.commons.exceptions.DukeException;
import duke.commons.file.FilePathNames;
import duke.commons.file.FilePaths;
import duke.commons.file.FileUtil;
import duke.model.Goal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;

import java.time.LocalDate;

/**
 * This object is in charge of all writing to save operations.
 */
public class Write {
    FilePaths filePaths = new FilePaths();
    private Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param meals the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void writeFile(MealList meals) throws DukeException {
        String toWriteStr = gson.toJson(meals.getMealTracker());
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_MEALS_FILE));
    }


    public void writeDefaults(MealList meals) throws DukeException {
        String toWriteStr = gson.toJson(meals.getStoredList());
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE));
    }

    public void writeGoal(User user) throws DukeException {
        Goal goal = user.getGoal();
        String toWriteStr = gson.toJson(goal);
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_GOAL_FILE));
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */
    public void writeUser(User user) throws DukeException {
        String toWriteStr = gson.toJson(user);
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE));
    }

    /**
     * Save all the recorded transactions.
     * @param wallet the database of all transactions.
     * @throws DukeException if error occurs.
     */
    public void writeTransaction(Wallet wallet) throws DukeException {
        String toWriteStr = gson.toJson(wallet);
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_TRANSACTION_FILE));
    }
}
