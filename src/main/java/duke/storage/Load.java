package duke.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import duke.commons.exceptions.DukeException;
import duke.commons.file.FilePathNames;
import duke.commons.file.FilePaths;
import duke.commons.file.FileUtil;
import duke.logic.autocorrect.Autocorrect;
import duke.model.Goal;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This object is in charge of all reading from save operations.
 */
public class Load {
    private BufferedReader bufferedReader = null;
    private String lineStr;
    private static FilePaths filePaths;
    // Flag to set if jar resource should be called if user file does not exist in host system.
    private static final boolean useResourceAsBackup = true;
    private Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();




    public Load() {
        filePaths = new FilePaths();
    }

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public void loadMealListData(MealList meals) throws DukeException {
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
            throw new DukeException("It appears the savefile has been corrupted. "
                    + "Previously recorded meals will not be loaded.");
        }
    }

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public void loadDefaultMealData(MealList meals) throws DukeException {
        String defaultMealFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE);
        Type defaultItemHashMap = new TypeToken<HashMap<String, HashMap<String, Integer>>>(){}.getType();
        bufferedReader = FileUtil.readFile(defaultMealFilePathStr, useResourceAsBackup);
        try {
            HashMap<String, HashMap<String, Integer>> data = gson.fromJson(bufferedReader,defaultItemHashMap);
            if (data != null) {
                meals.setStoredItems(data);
                bufferedReader.close();
            }
        } catch (Exception e) {
            throw new DukeException("It appears the savefile has been corrupted. "
                    + "Default meal values will not be loaded.");
        }
    }

    public void loadGoals(User user) throws DukeException {
        String goalFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_GOAL_FILE);
        Type goalType = new TypeToken<Goal>(){}.getType();
        bufferedReader = FileUtil.readFile(goalFilePathStr, useResourceAsBackup);
        try {
            Goal goal = gson.fromJson(bufferedReader, goalType);
            bufferedReader.close();
            if (goal != null) {
                user.setGoal(goal, true);
            }
        } catch (Exception e) {
            throw new DukeException("Error reading goal file");
        }
    }


    public void loadTransactions(Wallet wallet) throws DukeException {
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
            throw new DukeException("Error reading transactions file");
        }
    }

    public User loadUser() throws DukeException {
        String userFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE);
        User data;
        Type userType = new TypeToken<User>() {}.getType();
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
            throw new DukeException("Error reading user file.");
        }
    }

    public void loadAutoCorrect(Autocorrect autocorrect) throws DukeException {
        try {
            String autocorrectFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_AUTOCORRECT_FILE);
            bufferedReader = FileUtil.readFile(autocorrectFileStr, useResourceAsBackup);
            while ((lineStr = bufferedReader.readLine()) != null) {
                autocorrect.load(lineStr.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws DukeException {
        BufferedReader bufferedReader = LoadHelpUtil.load(specifiedHelp);
        try {
            while ((lineStr = bufferedReader.readLine()) != null) {
                lines.add(lineStr);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new DukeException("Error reading help file");
        }
    }
}
