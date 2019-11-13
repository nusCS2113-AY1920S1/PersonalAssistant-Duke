package diyeats.logic.dummy;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.MealList;
import diyeats.model.user.Gender;
import diyeats.model.user.Goal;
import diyeats.model.user.User;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * This is a stub user class for testing purposes.
 */
public class DummyUser extends User {
    public transient Goal goal = null;

    private HashMap<LocalDate, Double> weight = new HashMap<>();
    private  LocalDate lastDate = null;
    private int height = -1;
    private int age = -1;
    private Gender gender = null;
    private boolean isSetup;
    private String name = null;
    private int activityLevel = 5;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private double originalWeight = 0;

    public DummyUser(String name, int age, int height, Gender gender, int activityLevel,
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

    public DummyUser() {
        this.name = "Test Only";
        this.height = 174;
        this.age = 23;
        this.gender = Gender.MALE;
        this.isSetup = true;
        this.activityLevel = 2;
        this.originalWeight = 75;
        this.lastDate = LocalDate.now();
    }

    public boolean isValid() {
        return true;
    }

    public void setWeight(double weight) {
        LocalDate currentDate = LocalDate.now();
        this.weight.put(currentDate, weight);
        if (lastDate == null || lastDate.isBefore(currentDate)) {
            this.lastDate = currentDate;
        }
    }

    public void setWeight(double weight, LocalDate inputDate) {
        this.weight.put(inputDate, weight);
        if (lastDate == null || lastDate.isBefore(inputDate)) {
            this.lastDate = inputDate;
        }
    }

    public void setGoal(Goal goal) throws ProgramException {
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

    public HashMap<LocalDate, Double> getAllWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public int getActivityLevel() {
        return this.activityLevel;
    }

    @Override
    public int getDailyCalorie() {
        return 2000;
    }

    @Override
    public int getCalorieBalance() {
        return 1600;
    }

    @Override
    public double getActivityLevelDifference() {
        return 2;
    }

    public Goal getGoal() {
        return new Goal();
    }

    public Gender getGender() {
        return this.gender;
    }

    public boolean getIsSetup() {
        return this.isSetup;
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
    public int getTargetActivityLevel() {
        return 3;
    }

    @Override
    public String toString() {
        return this.name + "|" + this.height + "|" + this.age + "|" + this.gender  + "|" + this.isSetup
                + "|" + this.activityLevel + "|" + this.originalWeight + "|" + this.lastDate;
    }
}
