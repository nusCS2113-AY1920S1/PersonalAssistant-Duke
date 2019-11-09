package diyeats.storage;

import com.google.gson.Gson;
import diyeats.commons.exceptions.ProgramException;
import diyeats.commons.file.FilePathNames;
import diyeats.commons.file.FilePaths;
import diyeats.commons.file.FileUtil;
import diyeats.model.meal.MealList;
import diyeats.model.user.Goal;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;

//@@author Fractalisk

/**
 * This object is in charge of all writing to save operations.
 */
public class Write {
    FilePaths filePaths = new FilePaths();
    private Gson gson;

    public Write(Gson gson) {
        this.gson = gson;
    }

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param meals the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void writeFile(MealList meals) throws ProgramException {
        String toWriteStr = gson.toJson(meals.getMealTracker());
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_MEALS_FILE));
    }


    public void writeDefaults(MealList meals) throws ProgramException {
        String toWriteStr = gson.toJson(meals.getDefaultValues());
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE));
    }

    public void writeExercises(MealList meals) throws ProgramException {
        String toWriteStr = gson.toJson(meals.getExerciseList());
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_EXERCISES_FILE));
    }

    public void writeGoal(User user) throws ProgramException {
        Goal goal = user.getGoal();
        String toWriteStr = gson.toJson(goal);
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_GOAL_FILE));
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */
    public void writeUser(User user) throws ProgramException {
        String toWriteStr = gson.toJson(user);
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE));
    }

    /**
     * Save all the recorded transactions.
     * @param wallet the database of all transactions.
     * @throws ProgramException if error occurs.
     */
    public void writeTransaction(Wallet wallet) throws ProgramException {
        String toWriteStr = gson.toJson(wallet);
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_TRANSACTION_FILE));
    }

    public void test() {
        filePaths.setTestPathConfigMap();
    }
}
