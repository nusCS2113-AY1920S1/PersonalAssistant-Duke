package duke.model;

import duke.commons.exceptions.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Goal is a public class that defines all user set dietary goals.
 */
public class Goal {
    private String endDate;
    private String startDate;
    private double originalWeight;
    private double weightTarget;
    private int calorieTarget;
    private int caloriesLeft;
    private int caloriesConsumed;
    private int lifestyleTarget;

    public Goal() {
    }

    public Goal(String[] splitLine) throws DukeException {
        startDate = splitLine[0];
        endDate = splitLine[1];
        try {
            weightTarget = Double.parseDouble(splitLine[2]);
            calorieTarget = Integer.parseInt(splitLine[3]);
            lifestyleTarget = Integer.parseInt(splitLine[4]);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setWeightTarget(double weight) {
        this.weightTarget = weight;
    }

    public void setLifestyleTarget(int level) {
        this.lifestyleTarget = level;
    }

    public void setOriginalWeight(double originalWeight) {
        this.originalWeight = originalWeight;
    }

    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
    }

    public void updateStats(MealList meals) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate startDate = LocalDate.parse(this.startDate, formatter);
        LocalDate currentDate = LocalDate.now();
        int totalConsume = 0;
        HashMap<String, ArrayList<Meal>> mealTracker = meals.getMealTracker();
        for (LocalDate iterator = startDate; iterator.isBefore(currentDate) || iterator.isEqual(currentDate);
             iterator = iterator.plusDays(1)) {
            totalConsume += sumCaloriesInADay(mealTracker, iterator.format(formatter));
        }
        this.caloriesConsumed = totalConsume;
        this.caloriesLeft = this.calorieTarget - totalConsume;
    }

    private int sumCaloriesInADay(HashMap<String, ArrayList<Meal>> mealTracker, String iterator) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
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

    /**
     * This is a getter for date.
     * @return description of the task
     */
    public String getEndDate() {
        return this.endDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int daysElapsedSinceStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate startDate = LocalDate.parse(this.startDate, formatter);
        LocalDate currentDate = LocalDate.now();
        int daysElapsed = (int) DAYS.between(startDate,currentDate);
        return daysElapsed;
    }

    public int daysLeftToGoal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate endDate = LocalDate.parse(this.endDate, formatter);
        LocalDate currentDate = LocalDate.now();
        int daysLeft = (int) DAYS.between(currentDate,endDate);
        return daysLeft;
    }

    public int durationOfGoal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate startDate = LocalDate.parse(this.startDate, formatter);
        LocalDate endDate = LocalDate.parse(this.endDate, formatter);
        int duration = (int) DAYS.between(startDate,endDate);
        return duration;
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

    public int getLifestyleTarget() {
        return this.lifestyleTarget;
    }

    public double getWeightDifference() {
        return this.weightTarget - this.originalWeight;
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the status icon and the description of the goal
     */
    @Override
    public String toString() {
        return startDate + "|" + endDate + "|" + weightTarget + "|" + calorieTarget + "|" + lifestyleTarget;
    }

}
