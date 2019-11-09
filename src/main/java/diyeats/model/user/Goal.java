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

    public Goal() {
    }

    public Goal(HashMap<String, String> argumentsMap) {
        startDate = LocalDate.parse(argumentsMap.get(START_DATE_ARG_STR), LOCAL_DATE_FORMATTER);
        endDate = LocalDate.parse(argumentsMap.get(END_DATE_ARG_STR), LOCAL_DATE_FORMATTER);
        weightTarget = Double.parseDouble(argumentsMap.get(WEIGHT_ARG_STR));
        activityLevelTarget = Integer.parseInt(argumentsMap.get(ACTIVITY_ARG_STR));
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDate.parse(startDate, LOCAL_DATE_FORMATTER);
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDate.parse(endDate, LOCAL_DATE_FORMATTER);
    }

    public void setWeightTarget(double weight) {
        this.weightTarget = weight;
    }

    public void setActivityLevelTarget(int level) {
        this.activityLevelTarget = level;
    }

    public void setOriginalWeight(double originalWeight) {
        this.originalWeight = originalWeight;
    }

    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
    }

    /**
     * This is a getter for date.
     * @return description of the task
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public double getWeightTarget() {
        return this.weightTarget;
    }

    public int getCalorieTarget() {
        return this.calorieTarget;
    }

    public int getCaloriesLeft() {
        return this.caloriesLeft;
    }

    public int getCaloriesConsumed() {
        return this.caloriesConsumed;
    }

    public int getActivityLevelTarget() {
        return this.activityLevelTarget;
    }

    public double getWeightDifference() {
        return this.weightTarget - this.originalWeight;
    }

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

    private int sumCaloriesInADay(HashMap<LocalDate, ArrayList<Meal>> mealTracker, LocalDate iterator) {
        int caloriesConsumed = 0;
        if (!mealTracker.containsKey(iterator)) {
            caloriesConsumed += this.calorieTarget / durationOfGoal();
        } else {
            ArrayList<Meal> meals = mealTracker.get(iterator);
            if (meals.size() == 0) {
                caloriesConsumed += this.calorieTarget / durationOfGoal();
            } else {
                for (int i = 0; i < meals.size(); i += 1) {
                    caloriesConsumed += meals.get(i).getNutritionalValue().get("calorie");
                }
            }
        }
        return caloriesConsumed;
    }

    public int daysElapsedSinceStart() {
        LocalDate currentDate = LocalDate.now();
        int daysElapsed = (int) DAYS.between(startDate,currentDate);
        return daysElapsed;
    }

    public int daysLeftToGoal() {
        LocalDate currentDate = LocalDate.now();
        int daysLeft = (int) DAYS.between(currentDate,endDate);
        return daysLeft;
    }

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
