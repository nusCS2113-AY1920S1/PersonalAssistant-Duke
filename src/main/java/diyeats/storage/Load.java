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
    private static final boolean useResourceAsBackup = true;
    private Gson gson;

    public Load(Gson gson) {
        this.gson = gson;
    }

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws ProgramException if either the object is unable to open file or it is unable to read the file
     */
    public void loadMealListData(MealList meals) throws ProgramException {
        String userMealFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_MEALS_FILE);
        Type mealListHashMap = new TypeToken<HashMap<LocalDate, ArrayList<Meal>>>(){}.getType();
        bufferedReader = FileUtil.readFile(userMealFilePathStr, useResourceAsBackup);
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
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws ProgramException if either the object is unable to open file or it is unable to read the file
     */
    public void loadDefaultMealData(MealList meals) throws ProgramException {
        String defaultMealFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE);
        Type defaultItemHashMap = new TypeToken<HashMap<String, HashMap<String, Integer>>>(){}.getType();
        bufferedReader = FileUtil.readFile(defaultMealFilePathStr, useResourceAsBackup);
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

    public void loadExercises(MealList meals) throws ProgramException {
        String exercisesFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_EXERCISES_FILE);
        Type exerciseListType = new TypeToken<ExerciseList>(){}.getType();
        bufferedReader = FileUtil.readFile(exercisesFilePathStr, useResourceAsBackup);
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

    public void loadGoals(User user) throws ProgramException {
        String goalFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_GOAL_FILE);
        Type goalType = new TypeToken<Goal>(){}.getType();
        bufferedReader = FileUtil.readFile(goalFilePathStr, useResourceAsBackup);
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


    public void loadTransactions(Wallet wallet) throws ProgramException {
        String transactionsFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_TRANSACTION_FILE);
        Type walletType = new TypeToken<Wallet>(){}.getType();
        bufferedReader = FileUtil.readFile(transactionsFilePathStr, useResourceAsBackup);
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

    public User loadUser() throws ProgramException {
        String userFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE);
        User data;
        Type userType = new TypeToken<User>(){}.getType();
        bufferedReader = FileUtil.readFile(userFileStr, useResourceAsBackup);
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

    public void loadAutoCorrect(Autocorrect autocorrect) throws ProgramException {
        try {
            String autocorrectFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_AUTOCORRECT_FILE);
            bufferedReader = FileUtil.readFile(autocorrectFileStr, useResourceAsBackup);
            while ((lineStr = bufferedReader.readLine()) != null) {
                autocorrect.load(lineStr.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new ProgramException(e.getMessage());
        }
    }

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

    public void test() {
        filePaths.setTestPathConfigMap();
    }
}
