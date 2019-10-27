package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.model.*;
import duke.model.user.Gender;
import duke.model.user.User;

import java.math.BigDecimal;

public class LoadLineParser {

    /**
     * This function acts as a parser from the text file which is used to store data from the previous session.
     * @param line the line input from the input file
     * @param meals the structure that encapsulates the meal data for this session
     */
    public static void parse(MealList meals, String line) throws DukeException {
        String[] splitLine = line.split("\\|", 4);
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
        } else if (taskType.equals("G")) {
            meals.addGoal(new Goal(description, nutritionalValue[0], nutritionalValue), true);
        }
    }

    public static void parseTransactions(TransactionList transactionList, String line, User user) {
        String[] splitLine = line.split("\\|", 3);
        String transactionType = splitLine[0];
        String transactionAmount = splitLine[1];
        String transactionDate = splitLine[2];
        Transaction newTransaction;
        if (transactionType.equals("PAY")) {
            newTransaction = new Payment(transactionAmount, transactionDate);
            user.updateAccountBalance(newTransaction);
            LoadTransactionUtil.load(transactionList, newTransaction);
        } else if (transactionType.equals("DEP")) {
            newTransaction = new Deposit(transactionAmount, transactionDate);
            user.updateAccountBalance(newTransaction);
            LoadTransactionUtil.load(transactionList, newTransaction);
        }

    }

    public static User parseUser(String line) {
        String[] splitLine = line.split("\\|");
        String name = splitLine[0];
        int age = Integer.parseInt(splitLine[1]);
        int height = Integer.parseInt(splitLine[2]);
        int activityLevel = Integer.parseInt(splitLine[3]);
        boolean loseWeight = Boolean.parseBoolean(splitLine[4]);
        String sex = splitLine[5];
        BigDecimal accountBalance = new BigDecimal(splitLine[6]);
        if (sex.equals("M")) {
            return new User(name, age, height, Gender.MALE, activityLevel, loseWeight, accountBalance);
        } else {
            return new User(name, age, height, Gender.FEMALE, activityLevel, loseWeight, accountBalance);
        }
    }

}
