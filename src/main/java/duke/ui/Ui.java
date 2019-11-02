package duke.ui;

import duke.model.Goal;
import duke.model.meal.Meal;
import duke.model.user.User;
import duke.model.wallet.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import static duke.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

public class Ui {
    private static final String UI_PADDING = "     ";
    private static final String UI_BOUNDARY = "    ____________________________________________________________";
    public Scanner in = new Scanner(System.in);

    public void showLine() {
        System.out.println(UI_BOUNDARY);
    }

    void showPadding() {
        System.out.print(UI_PADDING);
    }

    public void showBye() {
        System.out.println(UI_BOUNDARY);
        System.out.println(UI_PADDING + "Bye. Hope to see you again soon!");
        System.out.println(UI_BOUNDARY);
    }

    public void showList(ArrayList<Meal> meals, String messageStr) {
        showPadding();
        System.out.println(messageStr);
        for (int i = 1; i <= meals.size(); i++) {
            Meal currentMeal = meals.get(i - 1);
            showPadding();
            System.out.println(i + ". " + currentMeal);
        }
    }

    public void showMealList(ArrayList<Meal> meals)  {
        String messageStr = "Here are the meals in your list: ";
        showList(meals, messageStr);
    }

    public void showSuggestedMealList(ArrayList<Meal> meals, LocalDate date) {
        String messageStr = "Here are the suggested meals for " + date.format(LOCAL_DATE_FORMATTER);
        showList(meals, messageStr);
        showLine();
        showMessage("Please select which meal you would like by providing "
                + "index of meal item in the list above.");
        showMessage("Enter 0 to decline suggestions.");

    }

    public void showDone(Meal currentMeal, ArrayList<Meal> meals) {
        System.out.println(UI_PADDING + "Nice! I've marked these tasks as done:");
        System.out.println(UI_PADDING + currentMeal);
    }

    public void showAdded(Meal currentMeal, ArrayList<Meal> meals, User user, LocalDate dateStr) {
        System.out.println(UI_PADDING + "Got it. I've added this meal:");
        System.out.println(UI_PADDING + currentMeal);
        showCaloriesLeft(meals, user, dateStr);
    }

    public void showUpdated(Meal newMeal, ArrayList<Meal> meals, User user, LocalDate dateStr) {
        System.out.println(UI_PADDING + "Got it. I've updated this old meal with this: " + newMeal);
        showCaloriesLeft(meals, user, dateStr);
    }

    /**
     * Display remaining calories for specified date based on current meals plan.
     * @param meals List of meals on the date.
     * @param user User information which we want to query.
     * @param dateStr Date in which remaining calories are computed.
     */
    public void showCaloriesLeft(ArrayList<Meal> meals, User user, LocalDate dateStr) {
        int totalActualConsume = 0;
        int totalPossibleConsume = 0;
        for (int i = 0; i < meals.size(); i += 1) {
            int currentMealCalorie = meals.get(i).getNutritionalValue().get("calorie");
            totalPossibleConsume += currentMealCalorie;
            if (meals.get(i).getIsDone()) {
                totalActualConsume += currentMealCalorie;
            }
        }
        System.out.println(UI_PADDING + "On " + dateStr.format(LOCAL_DATE_FORMATTER) + ", you have:");
        System.out.println(UI_PADDING + UI_PADDING + (user.getDailyCalorie() - totalActualConsume)
                + " calories left (marked as done)");
        System.out.println(UI_PADDING + UI_PADDING + (user.getDailyCalorie() - totalPossibleConsume)
                + " calories left (total)");
    }

    public void showAddedItem(Meal currentMeal) {
        System.out.println(UI_PADDING + "Got it. I've added the default values for this meal:");
        System.out.println(UI_PADDING + currentMeal);
    }

    public void showDeleted(Meal currentMeal, ArrayList<Meal> meals) {
        System.out.println(UI_PADDING + "Noted. I've removed this meal:");
        System.out.println(UI_PADDING + currentMeal);
        System.out.println(UI_PADDING + "Now you have " + meals.size()  + " meals in the list.");
    }

    public void showCleared(String startDateStr, String endDateStr) {
        System.out.println(UI_PADDING + "Noted. Meals from " + startDateStr + " to "
                + endDateStr + " have been cleared");
    }


    public void showMessage(String message) {
        System.out.println(UI_PADDING + message);
    }

    public void showLoadingError() {
        System.out.println(UI_PADDING + "Failed to load file.");
    }

    public void showLoadingTransactionError() {
        System.out.println(UI_PADDING + "Failed to load transaction file.");
    }

    public void showUserLoadingError() {
        System.out.println(UI_PADDING + "Unable to load user file.");
    }

    public void showCalorie(User user) {
        System.out.println(UI_PADDING + "This is your daily calorie limit: " + user.getDailyCalorie());
    }

    public void showHelp(ArrayList<String> helpLines) {
        helpLines.forEach(line -> {
            System.out.println(UI_PADDING + line);
        });
    }

    public void showAddedGoal(Goal goal) {
        System.out.println(UI_PADDING + "Got it. I've set the goal to be met:");
        System.out.println(UI_PADDING + goal);
    }

    public void showHistory(ArrayList<String> commandHistoryList) {
        if (commandHistoryList.isEmpty()) {
            System.out.println(UI_PADDING + "No commands in history");
        } else {
            System.out.println(UI_PADDING + "History of Commands you have typed: ");
            for (int idx = 0;idx < commandHistoryList.size();idx++) {
                System.out.println(UI_PADDING + UI_PADDING + (idx + 1) + ". " + commandHistoryList.get(idx));
            }
        }
    }

    public void showWeightUpdate(User user, int weight, String date) {
        System.out.println(UI_PADDING + user.getName() + ", your weight has been updated on "
                + date + " to " + weight + "kg.");
    }

    public void showRejected() {
        System.out.println(UI_PADDING + "Understood, I've stopped the update.");
    }

    public void showConfirmation(String weight, String date) {
        System.out.println(UI_PADDING + "You have entered " + weight + " on " + date + ".");
        System.out.println(UI_PADDING + "Would you like to overwrite the record on " + date + "?(Y/N)");
    }

    public void showTransactionAdded(Transaction transaction, BigDecimal accountBalance) {
        System.out.println(UI_PADDING + "Got it. I've added this transaction:");
        System.out.println(UI_PADDING + transaction);
        System.out.println(UI_PADDING + "Your account balance is: " + accountBalance + " SGD");
    }

    public void showActivityLevel() {
        System.out.println(UI_PADDING + "Input Activity Level");
        System.out.println(UI_PADDING + "1) Sedentary (Little or no exercise, desk job");
        System.out.println(UI_PADDING + "2) Lightly active (Light exercise/ sports 1-3 days/week");
        System.out.println(UI_PADDING + "3) Moderately active (Moderate exercise/ sports 6-7 days/week)");
        System.out.println(UI_PADDING + "4) Very active (Hard exercise every day, or exercising 2 xs/day) ");
        System.out.println(UI_PADDING + "5) Extra active (Hard exercise 2 or more times per day, or training for\n"
                + UI_PADDING + "   marathon, or triathlon, etc. )");
    }

    public void showQueryStartDate() {
        System.out.println(UI_PADDING + "Please input the start date of your plan:");
    }

    public void showQueryEndDate() {
        System.out.println(UI_PADDING + "Please input the end date of your plan:");
    }

    public void showQueryTargetWeight() {
        System.out.println(UI_PADDING + "Please enter your target weight by the end of your plan:");
    }

    public void showQueryTargetLifestyle() {
        System.out.println(UI_PADDING + "Would you like to include more exercise in conjunction with your diet?");
        System.out.println(UI_PADDING + "Incorporating increased levels of exercise in conjunction with a diet plan");
        System.out.println(UI_PADDING + "will result in healthier and more effective weight management!");
        System.out.println(UI_PADDING + "Please reply (yes/no):");
    }

    public void queryOverrideExistingGoal() {
        System.out.println(UI_PADDING + "It appears there is a preexisting goal already in place");
        System.out.println(UI_PADDING + "Would you like to override the old goal with the new one?");
        System.out.println(UI_PADDING + "Please reply (yes/no):");
    }

    public void failSetGoal() {
        System.out.println(UI_PADDING + "The setGoal Command has been aborted");
    }

    public void succeedSetGoal() {
        System.out.println(UI_PADDING + "The setGoal Command is successful");
    }

    public void showStats(User user) {
        if (user.getGoal() == null) {
            goalNotFound();
        } else {
            showStatsWithGoal(user);
        }
    }

    public void goalNotFound() {
        System.out.println(UI_BOUNDARY);
        System.out.println(UI_PADDING + "It appears that a diet plan has not been setup for this session of DIYeats.");
        System.out.println(UI_PADDING + "Please use the `setgoal` command to create a diet plan customized for you!");
        System.out.println(UI_BOUNDARY);
    }

    private void showStatsWithGoal(User user) {
        System.out.println(UI_BOUNDARY);
        System.out.println(UI_PADDING + "These are your user statistics as of today:");
        System.out.println(UI_PADDING + "Original weight: " + user.getOriginalWeight());
        System.out.println(UI_PADDING + "Current Weight: " + user.getWeight());
        System.out.println(UI_PADDING + "Target Weight: " + user.getWeightTarget());
        System.out.println(UI_PADDING + "Days left to end of diet plan: " + user.getDaysLeftToGoal());
        System.out.println(UI_PADDING + "Avg current calorie balance: "
                + user.getAverageCalorieBalance() + " calories");
        System.out.println(UI_PADDING + "Avg calorie balance that must be maintained to reach goal: "
                + user.getCalorieBalance() + " calories");
        showOnTrack(user);
        System.out.println(UI_BOUNDARY);
    }

    private void showOnTrack(User user) {
        if (user.getOriginalWeight() > user.getWeightTarget() && user.getWeight() > user.getWeightTarget()) {
            System.out.println(UI_PADDING + "Great Job! You are on track to reaching your diet goals!");
        } else if (user.getOriginalWeight() < user.getWeightTarget() && user.getWeight() < user.getWeightTarget()) {
            System.out.println(UI_PADDING + "Great Job! You are on track to reaching your diet goals!");
        } else {
            System.out.println(UI_PADDING + "Oh no, you are deviating from your diet plan. Try harder!");
        }
    }

    public void showExerciseRequired(int calories) {
        System.out.println(UI_BOUNDARY);
        System.out.println(UI_PADDING + "To meet your goals, you should do: " + calories + " calories");
        System.out.println(UI_PADDING + "worth of exercises today. All the best!");
        System.out.println(UI_BOUNDARY);
    }
}