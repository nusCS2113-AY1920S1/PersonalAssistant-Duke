package duke.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import duke.commons.exceptions.DukeException;
import duke.commons.file.FilePaths;
import duke.logic.autocorrect.Autocorrect;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static duke.commons.file.FilePaths.FilePathNames;

/**
 * This object is in charge of all reading from save operations.
 */
public class Load {
    private BufferedReader bufferedReader = null;
    private String lineStr;
    private static FilePaths filePaths;
    // Flag to set if jar resource should be called if user file does not exist in host system.
    private static final boolean useResourceAsBackup = true;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


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
        Type mealListHashMap = new TypeToken<HashMap<String, ArrayList<Meal>>>(){}.getType();
        bufferedReader = FileUtil.readFile(userMealFilePathStr, useResourceAsBackup);
        try {
            HashMap<String, ArrayList<Meal>> data = gson.fromJson(bufferedReader,mealListHashMap);
            if (data != null) {
                meals.setMealTracker(data);
                bufferedReader.close();
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public void loadDefaultMealData(MealList meals) throws DukeException {
        String defaultMealFilePathStr = filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE);
        Type defaultItemHashMap = new TypeToken<HashMap<String, HashMap<String, Integer>>>(){}.getType();
        bufferedReader = FileUtil.readFile(defaultMealFilePathStr, useResourceAsBackup);
        try {
            HashMap<String, HashMap<String, Integer>> data = gson.fromJson(bufferedReader,defaultItemHashMap);
            if (data != null) {
                meals.setStoredItems(data);
                bufferedReader.close();
            }
        } catch (Exception e) {
            throw new DukeException("It appears the savefile has been corrupted. " +
                    "Default meal values will not be loaded.");
        }
    }

    public void loadGoals(User user) throws DukeException {
        String goalFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_GOAL_FILE);
        bufferedReader = FileUtil.readFile(goalFilePathStr, useResourceAsBackup);
        try {
            lineStr = bufferedReader.readLine();
            LoadLineParser.parseGoal(user, lineStr);
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
    }


    public void loadTransactions(Wallet wallet) throws DukeException {
        String transactionFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_TRANSACTION_FILE);
        bufferedReader = FileUtil.readFile(transactionFilePathStr, useResourceAsBackup);
        try {
            lineStr = bufferedReader.readLine();
            wallet.setAccountBalance(lineStr);
            while ((lineStr = bufferedReader.readLine()) != null) {
                LoadLineParser.parseTransactions(lineStr, wallet);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User loadUser() throws DukeException {
        String userFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE);
        User tempUser;
        bufferedReader = FileUtil.readFile(userFileStr, useResourceAsBackup);
        try {
            lineStr = bufferedReader.readLine();
            tempUser = LoadLineParser.parseUser(lineStr);
            while ((lineStr = bufferedReader.readLine()) != null) {
                String[] splitWeightInfo = lineStr.split("\\|");
                tempUser.setWeight(Integer.parseInt(splitWeightInfo[1]), splitWeightInfo[0]);
            }
            bufferedReader.close();
            return tempUser;
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
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
