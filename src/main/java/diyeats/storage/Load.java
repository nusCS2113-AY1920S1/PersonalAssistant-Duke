package diyeats.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import diyeats.commons.exceptions.ProgramException;
import diyeats.commons.file.FilePathNames;
import diyeats.commons.file.FilePaths;
import diyeats.commons.file.FileUtil;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.model.meal.ExerciseList;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.Goal;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

//@@author Fractalisk

/**
 * This object is in charge of all reading from save operations.
 */
public class Load {
    private BufferedReader bufferedReader = null;
    private String lineStr;
    private static FilePaths filePaths = new FilePaths();
    // Flag to set if jar resource should be called if user file does not exist in host system.
    private boolean isUseResourceAsBackup = true;
    private Gson gson;

    public Load(Gson gson) {
        this.gson = gson;
    }

    /**
     * Loads all meal records from save file to MealList object.
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadMealListData(MealList meals) throws ProgramException {
        String userMealFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_MEALS_FILE);
        Type mealListHashMap = new TypeToken<HashMap<LocalDate, ArrayList<Meal>>>(){}.getType();
        bufferedReader = FileUtil.readFile(userMealFilePathStr, isUseResourceAsBackup);
        try {
            HashMap<LocalDate, ArrayList<Meal>> data = gson.fromJson(bufferedReader, mealListHashMap);
            if (data != null) {
                meals.setMealTracker(data);
                bufferedReader.close();
            }
        } catch (Exception e) {
            throw new ProgramException("It appears the savefile has been corrupted. "
                    + "Previously recorded meals will not be loaded.");
        }
    }

    /**
     * Loads all default meal values from save file to MealList object.
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadDefaultMealData(MealList meals) throws ProgramException {
        String defaultMealFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE);
        Type defaultItemHashMap = new TypeToken<HashMap<String, HashMap<String, Integer>>>(){}.getType();
        bufferedReader = FileUtil.readFile(defaultMealFilePathStr, isUseResourceAsBackup);
        try {
            HashMap<String, HashMap<String, Integer>> data = gson.fromJson(bufferedReader, defaultItemHashMap);
            if (data != null) {
                meals.setDefaultValues(data);
                bufferedReader.close();
            }
        } catch (Exception e) {
            throw new ProgramException("It appears the savefile has been corrupted. "
                    + "Default meal values will not be loaded.");
        }
    }

    /**
     * Loads all exercise records and exercise types from save file to MealList object.
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadExercises(MealList meals) throws ProgramException {
        String exercisesFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_EXERCISES_FILE);
        Type exerciseListType = new TypeToken<ExerciseList>(){}.getType();
        bufferedReader = FileUtil.readFile(exercisesFilePathStr, isUseResourceAsBackup);
        try {
            ExerciseList data = gson.fromJson(bufferedReader, exerciseListType);
            if (data != null) {
                meals.setExerciseList(data);
            }
        } catch (Exception e) {
            throw new ProgramException("It appears the savefile has been corrupted. "
                    + "Exercise history will not be loaded.");
        }
    }

    /**
     * Loads all previously established goal from savefile to user.
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadGoals(User user) throws ProgramException {
        String goalFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_GOAL_FILE);
        Type goalType = new TypeToken<Goal>(){}.getType();
        bufferedReader = FileUtil.readFile(goalFilePathStr, isUseResourceAsBackup);
        try {
            Goal goal = gson.fromJson(bufferedReader, goalType);
            bufferedReader.close();
            if (goal != null) {
                user.setGoal(goal);
            }
        } catch (Exception e) {
            throw new ProgramException("Error reading goal file" + e.getMessage());
        }
    }

    /**
     * Loads all transaction data from savefile to wallet.
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadTransactions(Wallet wallet) throws ProgramException {
        String transactionsFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_TRANSACTION_FILE);
        Type walletType = new TypeToken<Wallet>(){}.getType();
        bufferedReader = FileUtil.readFile(transactionsFilePathStr, isUseResourceAsBackup);
        try {
            Wallet data = gson.fromJson(bufferedReader, walletType);
            bufferedReader.close();
            if (data != null) {
                wallet.updateAccountBalance(data);
            }
        } catch (Exception e) {
            throw new ProgramException("Error reading transactions file");
        }
    }

    /**
     * Loads all user data from savefile and returns a instance of user.
     * @return the user object generated from savefile
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public User loadUser() throws ProgramException {
        String userFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE);
        User data;
        Type userType = new TypeToken<User>(){}.getType();
        bufferedReader = FileUtil.readFile(userFileStr, isUseResourceAsBackup);
        try {
            data = gson.fromJson(bufferedReader, userType);
            bufferedReader.close();
            if (data != null) {
                return data;
            } else {
                return new User();
            }
        } catch (Exception e) {
            throw new ProgramException("Error reading user file.");
        }
    }

    /**
     * Loads all autocorrectable words from file into autocorrect.
     * @param autocorrect the autocorrect object to be updated with autocorrectable words
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadAutoCorrect(Autocorrect autocorrect) throws ProgramException {
        try {
            String autocorrectFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_AUTOCORRECT_FILE);
            bufferedReader = FileUtil.readFile(autocorrectFileStr, isUseResourceAsBackup);
            while ((lineStr = bufferedReader.readLine()) != null) {
                autocorrect.load(lineStr.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new ProgramException(e.getMessage());
        }
    }

    /**
     * Parses a help file into an arrayList of Strings.
     * @param lines the arrayList container to store the contents of the help file
     * @param specifiedHelp the type of help requested
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws ProgramException {
        BufferedReader bufferedReader = LoadHelpUtil.load(specifiedHelp);
        try {
            while ((lineStr = bufferedReader.readLine()) != null) {
                lines.add(lineStr);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new ProgramException("Error reading help file");
        }
    }

    /**
     * This method is used for JUnit testing.
     * Sets all filepaths to test directory.
     */
    public void test() {
        filePaths.setTestPathConfigMap();
    }
}
