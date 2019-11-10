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

    /**
     * Constructor for Write class.
     * @param gson the gson object used for json transcription.
     */
    public Write(Gson gson) {
        this.gson = gson;
    }

    /**
     * Parse all meal records to json and update the relevant input/output file.
     * @param meals the structure storing the meal records
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeFile(MealList meals) throws ProgramException {
        String toWriteStr = gson.toJson(meals.getMealTracker());
        assert toWriteStr != null : "mealTracker in mealList cannot be converted to json";
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_MEALS_FILE));
    }

    /**
     * Parse all default meal values to json and update the relevant input/output file.
     * @param meals the structure storing the meal records
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeDefaults(MealList meals) throws ProgramException {
        String toWriteStr = gson.toJson(meals.getDefaultValues());
        assert toWriteStr != null : "defaultValues in mealList cannot be converted to json";
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE));
    }

    /**
     * Parse exerciseList to json and update the relevant input/output file.
     * @param meals the structure storing the meal records
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeExercises(MealList meals) throws ProgramException {
        String toWriteStr = gson.toJson(meals.getExerciseList());
        assert toWriteStr != null : "exerciseList in mealList cannot be converted to json";
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_EXERCISES_FILE));
    }

    /**
     * Parse goal to json and update the relevant input/output file.
     * @param user the structure storing all user parameters as well as goal
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeGoal(User user) throws ProgramException {
        Goal goal = user.getGoal();
        String toWriteStr = gson.toJson(goal);
        assert toWriteStr != null : "goal in User cannot be converted to json";
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_GOAL_FILE));
    }

    /**
     * Parse User to json and update the relevant input/output file.
     * @param user the structure storing all user parameters as well as goal
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeUser(User user) throws ProgramException {
        String toWriteStr = gson.toJson(user);
        assert toWriteStr != null : "User cannot be converted to json";
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE));
    }

    /**
     * Save all the recorded transactions.
     * @param wallet the database of all transactions.
     * @throws ProgramException if error occurs.
     */
    public void writeTransaction(Wallet wallet) throws ProgramException {
        String toWriteStr = gson.toJson(wallet);
        assert toWriteStr != null : "wallet cannot be converted to json";
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePathNames.FILE_PATH_TRANSACTION_FILE));
    }

    /**
     * This method is used for JUnit testing.
     * Sets all filepaths to test directory.
     */
    public void test() {
        filePaths.setTestPathConfigMap();
    }
}
