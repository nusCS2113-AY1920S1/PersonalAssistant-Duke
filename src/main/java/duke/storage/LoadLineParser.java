package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.model.Goal;
import duke.model.Item;
import duke.model.meal.Breakfast;
import duke.model.meal.Dinner;
import duke.model.meal.Lunch;
import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.Gender;
import duke.model.user.User;
import duke.model.wallet.Deposit;
import duke.model.wallet.Payment;
import duke.model.wallet.Transaction;
import duke.model.wallet.TransactionList;
import duke.model.wallet.Wallet;

import java.util.List;

public class LoadLineParser {

    //TODO: Fix SLAP issues parsing and other stuff

    /**
     * This function acts as a parser from the text file which is used to store data from the previous session.
     * @param lines the list of lines from the input file
     * @param meals the structure that encapsulates the meal data for this session
     */
    public static void parseMealList(MealList meals, List<String> lines) throws DukeException {
        if (lines.isEmpty()) {
            return;
        }
        for (String lineStr : lines) {
            if (lineStr == null) {
                continue;
            }
            try {
                String[] splitLine = lineStr.split("\\|", 4);
                String taskType = splitLine[0];
                String status = splitLine[1];
                String description = splitLine[2];
                String[] nutritionalValue = splitLine[3].split("\\|");
                Meal newMeal;
                if (taskType.equals("B")) {
                    newMeal = new Breakfast(description, nutritionalValue);
                    if (status.equals("1")) {
                        newMeal.markAsDone();
                    }
                    LoadMealUtil.load(meals, newMeal);
                } else if (taskType.equals("L")) {
                    newMeal = new Lunch(description, nutritionalValue);
                    if (status.equals("1")) {
                        newMeal.markAsDone();
                    }
                    LoadMealUtil.load(meals, newMeal);
                } else if (taskType.equals("D")) {
                    newMeal = new Dinner(description, nutritionalValue);
                    if (status.equals("1")) {
                        newMeal.markAsDone();
                    }
                    LoadMealUtil.load(meals, newMeal);
                } else if (taskType.equals("S")) {
                    newMeal = new Item(description, nutritionalValue);
                    meals.addStoredItem(newMeal);
                }
            } catch (Exception e) {
                throw new DukeException("It appears the storage files have been corrupted, data loaded may be erroneous"
                        + "or incomplete.");
            }
        }
    }

    public static void parseTransactions(String line, Wallet wallet) {
        String[] splitLine = line.split("\\|", 3);
        String transactionType = splitLine[0];
        String transactionAmount = splitLine[1];
        String transactionDate = splitLine[2];
        Transaction newTransaction;
        if (transactionType.equals("PAY")) {
            newTransaction = new Payment(transactionAmount, transactionDate);
            LoadTransactionUtil.load(wallet.getTransactions(), newTransaction);
        } else if (transactionType.equals("DEP")) {
            newTransaction = new Deposit(transactionAmount, transactionDate);
            LoadTransactionUtil.load(wallet.getTransactions(), newTransaction);
        }
    }

    public static void parseGoal(User user, String line) throws DukeException {
        try {
            if (line != null) {
                String[] splitLine = line.split("\\|");
                Goal goal = new Goal(splitLine);
                user.setGoal(goal, true);
            }
        } catch (Exception e) {
            throw new DukeException("It appears the goal file has been corrupted. No goal shall be set for"
                    + " this session");
        }
    }

    public static User parseUser(String line) {
        String[] splitLine = line.split("\\|");
        String name = splitLine[0];
        int age = Integer.parseInt(splitLine[1]);
        int height = Integer.parseInt(splitLine[2]);
        int activityLevel = Integer.parseInt(splitLine[3]);
        int originalWeight = Integer.parseInt(splitLine[4]);
        String sex = splitLine[5];
        String lastDate = splitLine[6];
        Gender gender;
        if (sex.equals("M")) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        return new User(name, age, height, gender, activityLevel, originalWeight, lastDate);
    }
}
