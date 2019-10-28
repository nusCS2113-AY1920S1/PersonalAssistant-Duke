package duke.ui;

import duke.model.Goal;
import duke.model.meal.Meal;
import duke.model.wallet.Transaction;
import duke.model.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String padding = "     ";
    private static final String boundary = "    ____________________________________________________________";

    public void showWelcome() {
        System.out.println(boundary);
        System.out.println(padding + "Hello! I'm DIYeats");
        System.out.println(padding + "What can I do for you?");
        System.out.println(boundary);
        System.out.println();
    }

    public void showLine() {
        System.out.println(boundary);
    }

    void showPadding() {
        System.out.print(padding);
    }

    public void showBye() {
        System.out.println(boundary);
        System.out.println(padding + "Bye. Hope to see you again soon!");
        System.out.println(boundary);
    }

    public void showList(ArrayList<Meal> meals)  {
        showPadding();
        System.out.println("Here are the meals in your list: ");
        for (int i = 1; i <= meals.size(); i++) {
            Meal currentMeal = meals.get(i - 1);
            showPadding();
            System.out.println(i + ". " + currentMeal);
        }
    }

    public void showDone(Meal currentMeal, ArrayList<Meal> meals) {
        System.out.println(padding + "Nice! I've marked these tasks as done:");
        System.out.println(padding + currentMeal);
    }

    public void showAdded(Meal currentMeal, ArrayList<Meal> meals, User user, String dateStr) {
        System.out.println(padding + "Got it. I've added this meal:");
        System.out.println(padding + currentMeal);
        showCaloriesLeft(meals, user, dateStr);
    }

    public void showUpdated(Meal newMeal, ArrayList<Meal> meals, User user, String dateStr) {
        System.out.println(padding + "Got it. I've updated this old meal with this: " + newMeal);
        showCaloriesLeft(meals, user, dateStr);
    }

    /**
     * Display remaining calories for specified date based on current meals plan.
     * @param meals List of meals on the date.
     * @param user User information which we want to query.
     * @param dateStr Date in which remaining calories are computed.
     */
    public void showCaloriesLeft(ArrayList<Meal> meals, User user, String dateStr) {
        int totalConsume = 0;
        for (int i = 0; i < meals.size(); i += 1) {
            if (meals.get(i).getIsDone()) {
                totalConsume += meals.get(i).getNutritionalValue().get("calorie");
            }
        }
        System.out.println(padding + "Now you have " + (user.getDailyCalorie()
                - totalConsume) + " calories left on " + dateStr);
    }

    public void showAddedItem(Meal currentMeal) {
        System.out.println(padding + "Got it. I've added the default values for this meal:");
        System.out.println(padding + currentMeal);
    }

    public void showDeleted(Meal currentMeal, ArrayList<Meal> meals) {
        System.out.println(padding + "Noted. I've removed this meal:");
        System.out.println(padding + currentMeal);
        System.out.println(padding + "Now you have " + meals.size()  + " meals in the list.");
    }

    public void showCleared(String startDateStr, String endDateStr) {
        System.out.println(padding + "Noted. Meals from " + startDateStr + " to "
                + endDateStr + " have been cleared");
    }

    public void showMessage(String message) {
        System.out.println(padding + message);
    }

    public void showLoadingError() {
        System.out.println(padding + "Failed to load file.");
    }

    public void showLoadinngTransactionError() {
        System.out.println(padding + "Failed to load transaction file.");
    }

    public void showUserLoadingError() {
        System.out.println(padding + "Unable to load user file.");
    }

    public void showCalorie(User user) {
        System.out.println(padding + "This is your daily calorie limit: " + user.getDailyCalorie());
    }

    public void showRemainingCalorie(ArrayList<Meal> mealsOfDay, User user, int remainingCalories) {
        int limit = user.getDailyCalorie();
        int consumeTotal = 0;
        for (int i = 0; i < mealsOfDay.size(); i++) {
            if (mealsOfDay.get(i).getIsDone()) {
                consumeTotal += mealsOfDay.get(i).getNutritionalValue().get("calorie");
            }
        }
        if (remainingCalories == -1) {
            System.out.println(padding + "You have this many calories left today: " + (limit - consumeTotal));
        } else {
            System.out.println(padding + "You have this many calories left today: "
                    + (remainingCalories - consumeTotal));
        }
    }

    public void showHelp(ArrayList<String> helpLines) {
        helpLines.forEach(line -> {
            System.out.println(padding + line);
        });
    }

    public void showAddedGoal(Goal goal) {
        System.out.println(padding + "Got it. I've set the goal to be met:");
        System.out.println(padding + goal);
    }

    public void showHistory(ArrayList<String> commandHistoryList) {
        if (commandHistoryList.isEmpty()) {
            System.out.println(padding + "No commands in history");
        } else {
            System.out.println(padding + "History of Commands you have typed: ");
            for (int idx = 0;idx < commandHistoryList.size();idx++) {
                System.out.println(padding + padding + (idx + 1) + ". " + commandHistoryList.get(idx));
            }
        }
    }

    public void showWeightUpdate(User user, int weight, String date) {
        System.out.println(padding + user.getName() + ", your weight has been updated on "
                + date + " to " + weight + "kg.");
    }

    public void showRejected() {
        System.out.println(padding + "Understood, I've stopped the update.");
    }

    public void showConfirmation(String weight, String date) {
        System.out.println(padding + "You have entered " + weight + " on " + date + ".");
        System.out.println(padding + "Would you like to overwrite the record on " + date + "?(Y/N)");
    }

    public void showTransactionAdded(Transaction transaction, BigDecimal accountBalance) {
        System.out.println(padding + "Got it. I've added this transaction:");
        System.out.println(padding + transaction);
        System.out.println(padding + "Your account balance is: " + accountBalance + " SGD");
    }

}