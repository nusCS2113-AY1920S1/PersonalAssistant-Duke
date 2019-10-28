package duke.model.user;

import duke.commons.exceptions.DukeException;
import duke.model.Goal;
import duke.model.meal.MealList;
import duke.model.wallet.Account;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * This is a class that will store user information to be used for processing.
 */
public class User {
    private HashMap<String, Double> weight = new HashMap();
    private int height = 0;
    private int age = 0;
    private Gender gender = null;
    private boolean isSetup;
    private String name = null;
    private int activityLevel = 5;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private Account account;
    private Goal goal = null;
    private String lastDate = "28/10/2019";
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
    public User(String name, int age, int height, Gender gender, int activityLevel, double originalWeight) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.isSetup = true;
        this.activityLevel = activityLevel;
        this.originalWeight = originalWeight;
    }

    /**
     * This is a function to update weight at time of input.
     * @param weight Weight at time of input
     */
    public void setWeight(double weight) {
        Calendar calendarDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendarDate.getTime());
        this.weight.put(currentDate, weight);
        Date lastDateDate = new Date();
        try {
            lastDateDate = dateFormat.parse(lastDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar lastDateCalendar = Calendar.getInstance();
        lastDateCalendar.setTime(lastDateDate);
        if (calendarDate.after(lastDateCalendar)) {
            this.lastDate = currentDate;
        }
    }

    /**
     * This is a function to update weight at input date.
     * @param weight Weight at time of input
     * @param date String of the date in DD/MM/YYYY format
     */
    public void setWeight(double weight, String date) throws DukeException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date temp;
        try {
            temp = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException(e.getMessage());
        }
        String currentDate = dateFormat.format(temp.getTime());
        Date lastDateDate = new Date();
        try {
            lastDateDate = dateFormat.parse(lastDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.weight.put(currentDate, weight);
        Calendar lastDateCalendar = Calendar.getInstance();
        lastDateCalendar.setTime(lastDateDate);

        Date currDateDate = new Date();
        try {
            currDateDate = dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar currDateCalendar = Calendar.getInstance();
        currDateCalendar.setTime(currDateDate);
        if (lastDateCalendar.before(currDateCalendar)) {
            this.lastDate = date;
        }
    }

    public boolean setGoal(Goal goal, boolean override) {
        if (this.goal != null && override == false) {
            return false;
        } else {
            this.goal = goal;
            this.goal.setOriginalWeight(originalWeight);
            calculateTargetCalories();
            return true;
        }
    }

    //TODO: might want to refactor this to make it more cohesive (1 degree of separation only)
    public void depositToAccount(BigDecimal depositAmount) {
        this.account.deposit(depositAmount);
    }

    public void setOriginalWeight(double originalWeight) {
        this.originalWeight = originalWeight;
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

    public void updateStats(MealList meals) {
        this.goal.updateStats(meals);
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setIsSetup() {
        this.isSetup = true;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getLastDate() {
        return this.lastDate;
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
    public HashMap<String, Double> getAllWeight() {
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
        return (int)(this.factor[this.activityLevel] * calorie);
    }

    public int getCalorieChangeToReachTarget() {
        return (int) (7700 * (getWeightTarget() - getWeight()));
    }

    public int getAvgCalorieChangeToReachTarget() {
        if (getDaysLeftToGoal() == 0) {
            return 0;
        } else {
            return getCalorieChangeToReachTarget() / getDaysLeftToGoal();
        }
    }

    public void calculateTargetCalories() {
        int target = getDailyCalorie() * goal.durationOfGoal()
                - getCalorieChangeToReachTarget();
        goal.setCalorieTarget(target);
    }

    public int getCalorieBalance() {
        return getDailyCalorie() + getAvgCalorieChangeToReachTarget();
    }

    public int getAverageCalorieBalance() {
        return goal.getCaloriesConsumed() / (goal.daysElapsedSinceStart() + 1);
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
}
