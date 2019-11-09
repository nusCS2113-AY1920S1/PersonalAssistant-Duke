package diyeats.model.user;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.wallet.Account;

import java.time.LocalDate;
import java.util.HashMap;

//@@author
/**
 * This is a class that will store user information to be used for processing.
 */
public class User {
    public transient Goal goal = null;

    private HashMap<LocalDate, Double> weight = new HashMap();
    private  LocalDate lastDate = null;
    private int height = -1;
    private int age = -1;
    private Gender gender = null;
    private boolean isSetup;
    private String name = null;
    private int activityLevel = 5;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private double originalWeight = 0;


    /**
     * This is a contructor to create an empty user profile.
     */
    public User() {
        this.isSetup = false;
    }

    /**
     * This is a contructor to create an user profile with all the info.
     * Used during loading.
     * @param name name of user
     * @param age age of user
     * @param height height of user
     * @param gender biological gender of user
     * @param activityLevel how active the user is
     * @param originalWeight their original weight
     */
    public User(String name, int age, int height, Gender gender, int activityLevel,
                double originalWeight, LocalDate lastDate) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.isSetup = true;
        this.activityLevel = activityLevel;
        this.originalWeight = originalWeight;
        this.lastDate = lastDate;
    }

    public boolean isValid() {
        if (name != null && age != -1 && getAllWeight().size() != 0
                && height != -1 && gender != null && activityLevel != 5) {
            isSetup = true;
            return true;
        } else {
            isSetup = false;
            return false;
        }
    }

    /**
     * This is a function to update weight at time of input.
     * @param weight Weight at time of input
     */
    public void setWeight(double weight) {
        LocalDate currentDate = LocalDate.now();
        this.weight.put(currentDate, weight);
        if (lastDate == null || lastDate.isBefore(currentDate)) {
            this.lastDate = currentDate;
        }
    }

    /**
     * This is a function to update weight at input date.
     * @param weight Weight at time of input
     * @param inputDate LocalDate of the date in DD/MM/YYYY format
     */
    public void setWeight(double weight, LocalDate inputDate) {
        this.weight.put(inputDate, weight);
        if (lastDate == null || lastDate.isBefore(inputDate)) {
            this.lastDate = inputDate;
        }
    }

    public void setGoal(Goal goal) throws ProgramException {
        if (goal.getActivityLevelTarget() < activityLevel && activityLevel != 5) {
            throw new ProgramException("Set goal failed, cannot set target activity level to\n"
                    + "     be lower than current activity level.");
        } else if (!checkGoalFeasibility(goal)) {
            throw new ProgramException("Set goal failed. Average calorie loss in a day\n"
                    + "     must not exceed 40% of your current calorie expenditure!");
        } else {
            this.goal = goal;
            if (isSetup) {
                this.goal.setOriginalWeight(originalWeight);
                calculateTargetCalories();
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void updateStats(MealList meals) {
        this.goal.updateStats(meals);
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public double getWeight() {
        return this.weight.get(getLastDate());
    }

    public double getOriginalWeight() {
        return this.originalWeight;
    }

    public double getWeightTarget() {
        return goal.getWeightTarget();
    }

    public int getDaysLeftToGoal() {
        return goal.daysLeftToGoal();
    }

    /**
     * This is a function to obtain all the weight at different date.
     */
    public HashMap<LocalDate, Double> getAllWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public int getActivityLevel() {
        return this.activityLevel;
    }

    public int getDailyCalorie() {
        double calorie;
        if (this.gender == Gender.MALE) {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() + 5;
        } else {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() - 161;
        }
        if (goal == null || goal.getActivityLevelTarget() == 5) {
            return (int) (this.factor[this.activityLevel] * calorie);
        } else {
            return (int) (this.factor[goal.getActivityLevelTarget()] * calorie);
        }
    }

    public int getCalorieBalance() {
        return getDailyCalorie() + getAvgCalorieChangeToReachTarget();
    }

    public double getActivityLevelDifference() {
        return this.factor[goal.getActivityLevelTarget()] - this.factor[this.activityLevel];
    }

    public Goal getGoal() {
        return goal;
    }

    public Gender getGender() {
        return this.gender;
    }

    /**
     * This is a function to check if it's an empty profile.
     */
    public boolean getIsSetup() {
        return this.isSetup;
    }

    private boolean checkGoalFeasibility(Goal goal) {
        double calorieLossPerDayToReachWeight = 7700 * (getWeight() - goal.getWeightTarget()) / goal.durationOfGoal();
        double maximumCalorieLossPerDay = 0.4 * getDailyCalorie();
        return calorieLossPerDayToReachWeight < maximumCalorieLossPerDay;
    }

    private int getCalorieChangeToReachTarget() {
        return (int) (7700 * (getWeight() - getWeightTarget()));
    }

    private int getAvgCalorieChangeToReachTarget() {
        if (getDaysLeftToGoal() == 0) {
            return 0;
        } else {
            return getCalorieChangeToReachTarget() / getDaysLeftToGoal();
        }
    }

    private void calculateTargetCalories() {
        int target = getDailyCalorie() * this.goal.durationOfGoal()
                - getCalorieChangeToReachTarget();
        this.goal.setCalorieTarget(target);
    }

    private LocalDate getLastDate() {
        if (this.lastDate == null) {
            lastDate = LocalDate.now();
            return lastDate;
        } else {
            return this.lastDate;
        }
    }

    @Override
    public String toString() {
        return this.name + "|" + this.height + "|" + this.age + "|" + this.gender  + "|" + this.isSetup
                + "|" + this.activityLevel + "|" + this.originalWeight + "|" + this.lastDate;
    }
}
