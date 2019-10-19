package duke.ui;

import duke.model.Goal;
import duke.model.Meal;
import duke.model.user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String padding = "     ";
    private static final String boundary = "    ____________________________________________________________";
    public Scanner in = new Scanner(System.in);

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
        System.out.println("     " + "Bye. Hope to see you again soon!");
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

    public void showDone(ArrayList<Meal> meals) {
        showPadding();
        System.out.println("Nice! I've marked these tasks as done:");
        for (int i = 1; i <= meals.size(); i++) {
            Meal currentMeal = meals.get(i - 1);
            showPadding();
            System.out.println(i + ". " + currentMeal);
        }
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
            totalConsume += meals.get(i).getNutritionalValue().get("calorie");
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

    public String readCommand(Scanner in) {
        String input = in.nextLine();
        return input;
    }

    public void showMessage(String message) {
        System.out.println(padding + message);
    }

    public void showLoadingError() {
        System.out.println(padding + "Failed to load file.");
    }

    public void showUserLoadingError() {
        System.out.println(padding + "Unable to load user file.");
    }

    public void showWelcomeNew() {
        System.out.println(padding + "Welcome!");
        System.out.println(padding + "I see that you're new.");
        System.out.println(padding + "Please enter your particulars to get started!");
    }

    public void showWelcomeBack(User user) {
        System.out.println(padding + "Welcome back, "  + user.getName());
    }

    public void showUserSetupDone(User user) {
        System.out.println(padding + "Thanks, " + user.getName() + "!");
        System.out.println(padding + "We are done setting up!");
    }

    public void showCalorie(User user) {
        System.out.println(padding + "This is your daily calorie limit: " + user.getDailyCalorie());
    }

    public void showRemainingCalorie(ArrayList<Meal> mealsOfDay, User user, int remainingCalories) {
        int limit = user.getDailyCalorie();
        int consumeTotal = 0;
        for (int i = 0; i < mealsOfDay.size(); i += 1) {
            consumeTotal += mealsOfDay.get(i).getNutritionalValue().get("calorie");
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
}