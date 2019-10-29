package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.commons.file.FilePaths;
import duke.commons.file.FileUtil;
import duke.model.Goal;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.Gender;
import duke.model.user.User;
import duke.model.wallet.Transaction;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This object is in charge of all writing to save operations.
 */
public class Write {
    FilePaths filePaths = new FilePaths();

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param mealData the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void writeFile(MealList mealData) throws DukeException {
        HashMap<String, ArrayList<Meal>> meals = mealData.getMealTracker();
        String toWriteStr = "";
        for (String i : meals.keySet()) { //write process for stored food entries
            ArrayList<Meal> mealsInDay = meals.get(i);
            for (int j = 0; j < meals.get(i).size(); j++) {
                String tempLineStr = "";
                Meal currentMeal = mealsInDay.get(j);
                String status = "0";
                if (currentMeal.getIsDone()) {
                    status = "1";
                }
                tempLineStr += currentMeal.getType() + "|" + status + "|" + currentMeal.getDescription()
                        + "|date|" + currentMeal.getDate();
                HashMap<String, Integer> nutritionData = currentMeal.getNutritionalValue();
                if (nutritionData.size() != 0) {
                    tempLineStr += "|";
                    for (String k : nutritionData.keySet()) {
                        tempLineStr += k + "|" + nutritionData.get(k) + "|";
                    }
                    toWriteStr += tempLineStr.substring(0, tempLineStr.length() - 1) + "\n";
                }
            }
        }

        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_USER_MEALS_FILE));
    }


    public void writeDefaults(MealList user) throws DukeException {
        HashMap<String, HashMap<String, Integer>> storedItems = user.getStoredList();
        String toWriteStr = "";
        for (String i : storedItems.keySet()) {
            //write process for stored default food values
            String tempLineStr = "";
            tempLineStr += "S|0|" + i;
            HashMap<String, Integer> nutritionData = storedItems.get(i);
            if (nutritionData.size() != 0) {
                tempLineStr += "|";
                for (String k : nutritionData.keySet()) {
                    tempLineStr += k + "|" + nutritionData.get(k) + "|";
                }
                toWriteStr += tempLineStr.substring(0, tempLineStr.length() - 1) + "\n";
            }
        }
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_DEFAULT_MEAL_FILE));
    }

    public void writeGoal(User user) throws DukeException {
        Goal goal = user.getGoal();
        String toWriteStr = goal.toString();
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_GOAL_FILE));
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */
    public void writeUser(User user) throws DukeException {
        String toWriteStr = user.getName() + "|" + user.getAge() + "|"
                + user.getHeight() + "|" + user.getActivityLevel() + "|" + user.getOriginalWeight() + "|";
        if (user.getGender() == Gender.MALE) {
            toWriteStr += "M";
        } else {
            toWriteStr += "F";
        }
        toWriteStr += "|" + user.getLastDate();
        HashMap<String, Double> allWeight = user.getAllWeight();
        Iterator iterator = allWeight.keySet().iterator();
        while (iterator.hasNext()) {
            toWriteStr += "\n";
            String date = (String) iterator.next();
            double weight = allWeight.get(date);
            toWriteStr += date + "|" + weight;
        }
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_USER_FILE));
    }

    /**
     * Save all the recorded transactions.
     * @param wallet the database of all transactions.
     * @throws DukeException if error occurs.
     */
    public void writeTransaction(Wallet wallet) throws DukeException {
        HashMap<String, ArrayList<Transaction>> transactions = wallet.getTransactions().getTransactionTracker();
        String toWriteStr = wallet.getAccountBalance() + "\n";
        for (String i : transactions.keySet()) {
            ArrayList<Transaction> transactionInADay = transactions.get(i);
            for (int j = 0; j < transactions.get(i).size(); j++) {
                Transaction currentTransaction = transactionInADay.get(j);
                toWriteStr += currentTransaction.getType() + "|" + currentTransaction.getTransactionAmount()
                        + "|" + currentTransaction.getDate() + "\n";
            }
        }
        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FilePathNames.FILE_PATH_TRANSACTION_FILE));
    }
}
