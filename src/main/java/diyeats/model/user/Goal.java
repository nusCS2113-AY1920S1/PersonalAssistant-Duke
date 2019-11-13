package diyeats.model.user;

import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;
import static diyeats.commons.constants.GoalCommandSyntax.ACTIVITY_ARG_STR;
import static diyeats.commons.constants.GoalCommandSyntax.END_DATE_ARG_STR;
import static diyeats.commons.constants.GoalCommandSyntax.START_DATE_ARG_STR;
import static diyeats.commons.constants.GoalCommandSyntax.WEIGHT_ARG_STR;
import static java.time.temporal.ChronoUnit.DAYS;

//@@author Fractalisk

/**
 * Goal is a public class that defines all user set dietary goals.
 */
public class Goal {
    private LocalDate endDate;
    private LocalDate startDate;
    private double originalWeight;
    private double weightTarget;
    private int calorieTarget;
    private int caloriesLeft;
    private int caloriesConsumed;
    private int activityLevelTarget;

    /**
     * Empty constructor for Goal used in testing.
     */
    public Goal() {
    }

    /**
     * Constructor for Goal.
     * @param argumentsMap HashMap of arguments to parse as goal details
     */
    public Goal(HashMap<String, String> argumentsMap) {
        startDate = LocalDate.parse(argumentsMap.get(START_DATE_ARG_STR), LOCAL_DATE_FORMATTER);
        endDate = LocalDate.parse(argumentsMap.get(END_DATE_ARG_STR), LOCAL_DATE_FORMATTER);
        weightTarget = Double.parseDouble(argumentsMap.get(WEIGHT_ARG_STR));
        //activity level is stored as a range from 0 - 4
        activityLevelTarget = Integer.parseInt(argumentsMap.get(ACTIVITY_ARG_STR)) - 1;
    }

    /**
     * Setter for originalWeight.
     * @param originalWeight original weight of user
     */
    public void setOriginalWeight(double originalWeight) {
        this.originalWeight = originalWeight;
    }

    /**
     * Setter for calorieTarget.
     * @param calorieTarget calories user must consume to reach target
     */
    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
    }

    /**
     * Calculate average calorie consumed per day.
     * @return calories consumed per day
     */
    public int getAverageCalorieBalance() {
        return this.caloriesConsumed / (daysElapsedSinceStart() + 1);
    }

    /**
     * This is a getter for date.
     * @return description of the task
     */
    public double getWeightTarget() {
        return this.weightTarget;
    }

    /**
     * Getter for activityLeveltarget.
     * @return activity level target designated by user
     */
    public int getActivityLevelTarget() {
        return this.activityLevelTarget;
    }

    /**
     * Updates caloriesConsumed variable and caloriesLeft.
     * @param meals the container storing all meal records
     */
    public void updateStats(MealList meals) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate currentDate = LocalDate.now();
        int totalConsume = 0;
        HashMap<LocalDate, ArrayList<Meal>> mealTracker = meals.getMealTracker();
        for (LocalDate iterator = startDate; iterator.isBefore(currentDate) || iterator.isEqual(currentDate);
             iterator = iterator.plusDays(1)) {
            totalConsume += sumCaloriesInADay(mealTracker, iterator);
        }
        this.caloriesConsumed = totalConsume;
        this.caloriesLeft = this.calorieTarget - totalConsume;
    }

    /**
     * Calculate sum of calorie consumed in one day.
     * @param mealTracker container for all meal records
     * @param iterator date to check for meal records
     * @return total calories consumed on that day
     */
    private int sumCaloriesInADay(HashMap<LocalDate, ArrayList<Meal>> mealTracker, LocalDate iterator) {
        int caloriesConsumed = 0;
        if (!mealTracker.containsKey(iterator)) {
            /*If a day does not contain any data, assume the user as consumed the average amount
            of calories to reach goal on that day */
            caloriesConsumed += this.calorieTarget / durationOfGoal();
        } else {
            ArrayList<Meal> meals = mealTracker.get(iterator);
            if (meals.size() == 0) {
                /*If a day does not contain any data, assume the user as consumed the average amount
                 of calories to reach goal on that day */
                caloriesConsumed += this.calorieTarget / durationOfGoal();
            } else {
                for (int i = 0; i < meals.size(); i += 1) {
                    caloriesConsumed += meals.get(i).getNutritionalValue().get("calorie");
                }
            }
        }
        return caloriesConsumed;
    }

    /**
     * Calculates days since start of goal to now.
     * @return number of days since start
     */
    private int daysElapsedSinceStart() {
        LocalDate currentDate = LocalDate.now();
        int daysElapsed = (int) DAYS.between(startDate,currentDate);
        return daysElapsed;
    }

    /**
     * Calculates days left to end of goal from now.
     * @return number of days left
     */
    public int daysLeftToGoal() {
        LocalDate currentDate = LocalDate.now();
        int daysLeft = (int) DAYS.between(currentDate,endDate);
        return daysLeft;
    }

    /**
     * Calculates days between start and end date of goal.
     * @return duration of goal
     */
    public int durationOfGoal() {
        int duration = (int) DAYS.between(startDate,endDate);
        return duration;
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the status icon and the description of the goal
     */
    @Override
    public String toString() {
        return startDate + "|" + endDate + "|" + weightTarget + "|" + calorieTarget + "|" + activityLevelTarget;
    }

}
