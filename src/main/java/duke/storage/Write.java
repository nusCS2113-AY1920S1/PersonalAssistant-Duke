package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.model.*;
import duke.model.user.Gender;
import duke.model.user.Tuple;
import duke.model.user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static duke.commons.FilePaths.*;

/**
 * This object is in charge of all writing to save operations.
 */
public class Write {
    private BufferedWriter bufferedWriter = null;

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param mealData the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void writeFile(MealList mealData) {
        HashMap<String, ArrayList<Meal>> meals = mealData.getMealTracker();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(DATA_FILE));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : meals.keySet()) { //write process for stored food entries
                ArrayList<Meal> mealsInDay = meals.get(i);
                for (int j = 0; j < meals.get(i).size(); j++) {
                    Meal currentMeal = mealsInDay.get(j);
                    String status = "0";
                    if (currentMeal.getIsDone()) {
                        status = "1";
                    }
                    String toWrite = currentMeal.getType() + "|" + status + "|" + currentMeal.getDescription()
                            + "|date|" + currentMeal.getDate();
                    HashMap<String, Integer> nutritionData = currentMeal.getNutritionalValue();
                    if (nutritionData.size() != 0) {
                        toWrite += "|";
                        for (String k : nutritionData.keySet()) {
                            toWrite += k + "|" + nutritionData.get(k) + "|";
                        }
                        toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
                    }
                    bufferedWriter.write(toWrite);
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void writeDefaults(MealList mealData) {
        HashMap<String, HashMap<String, Integer>> storedItems = mealData.getStoredList();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(DEFAULTS_FILE));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : storedItems.keySet()) { //write process for stored default food values
                String toWrite = "";
                toWrite += "S|0|" + i;
                HashMap<String, Integer> nutritionData = storedItems.get(i);
                if (nutritionData.size() != 0) {
                    toWrite += "|";
                    for (String k : nutritionData.keySet()) {
                        toWrite += k + "|" + nutritionData.get(k) + "|";
                    }
                    toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
                }
                bufferedWriter.write(toWrite);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void writeGoal(MealList mealData) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(GOAL_FILE));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            Goal goal = mealData.getGoal();
            String toWrite = "G|0|" + goal.getEndDate() + "|" + goal.getStartDate();
            HashMap<String, Integer> nutritionData = goal.getNutritionalValue();
            if (nutritionData.size() != 0) {
                for (String k : nutritionData.keySet()) {
                    toWrite += k + "|" + nutritionData.get(k) + "|";
                }
                toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
            }
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }


    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */

    public void writeUser(User user) throws DukeException {
        String toWrite = user.getName() + "|" + user.getAge() + "|"
                + user.getHeight() + "|" + user.getActivityLevel() + "|" + user.getLoseWeight() + "|";
        if (user.getSex() == Gender.MALE) {
            toWrite += "M";
        } else {
            toWrite += "F";
        }
        toWrite += "|" + user.getAccountBalance();
        ArrayList<Tuple> allWeight = user.getAllWeight();
        for (int i = 0; i < user.getAllWeight().size(); i += 1) {
            toWrite += "\n";
            String date = allWeight.get(i).date;
            int weight = allWeight.get(i).weight;
            toWrite += date + "|" + weight;
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(USER_FILE));
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void writeTransaction(TransactionList transactionList) throws DukeException {
        HashMap<String, ArrayList<Transaction>> transactions = transactionList.getTransactionTracker();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(TRANSACTION_FILE));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : transactions.keySet()) {
                ArrayList<Transaction> transactionInADay = transactions.get(i);
                for (int j = 0; j < transactions.get(i).size(); j++) {
                    Transaction currentTransaction = transactionInADay.get(j);
                    String toWrite = currentTransaction.getType() + "|" + currentTransaction.getTransactionAmount()
                            + "|" + currentTransaction.getDate() + "\n";
                    bufferedWriter.write(toWrite);
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
