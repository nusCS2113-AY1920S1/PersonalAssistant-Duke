package duke.model.user;

import duke.commons.exceptions.DukeException;
import duke.model.wallet.Account;
import duke.model.wallet.Transaction;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is a class that will store user information to be used for processing.
 */

public class User {
    private HashMap<String, Integer> weight = new HashMap();
    private int height = 0;
    private int age = 0;
    private Gender sex = null;
    private boolean isSetup;
    private String name = null;
    private int activityLevel = 5;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private boolean loseWeight = false;
    private boolean hasSetMaintain = false;
    private String lastDate = "28/10/2019";

    /**
     * This is a contructor to create an empty user profile.
     * @Author Foo Chi Hen
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
     * @param sex biological gender of user
     * @param activityLevel how active the user is
     * @param loseWeight if they would like to lose weight or maintain
     */

    public User(String name, int age, int height, Gender sex, int activityLevel, boolean loseWeight) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.sex = sex;
        this.isSetup = true;
        this.activityLevel = activityLevel;
        this.loseWeight = loseWeight;
    }

    /**
     * This is a function to update weight at time of input.
     * @param weight Weight at time of input
     */

    public void setWeight(int weight) {
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

    public void setWeight(int weight, String date) throws DukeException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date temp;
        try {
            temp = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException(e.getMessage());
        }
        String currentDate = dateFormat.format(temp.getTime());
        this.weight.put(currentDate, weight);

        Date lastDateDate = new Date();
        try {
            lastDateDate = dateFormat.parse(lastDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.sex = gender;
    }

    public void setLoseWeight(boolean loseWeight) {
        this.loseWeight = loseWeight;
    }

    public void setHasSetMaintain(boolean hasSetMaintain) {
        this.hasSetMaintain = hasSetMaintain;
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

    public int getWeight() {
        Calendar calendarDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendarDate.getTime());
        return this.weight.get(getLastDate());
    }

    /**
     * This is a function to obtain all the weight at different date.
     */

    public HashMap<String, Integer> getAllWeight() {
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
        if (this.sex == Gender.MALE) {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() + 5;
        } else {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() - 161;
        }
        return (int)(((this.loseWeight) ? 0.8 : 1) * this.factor[this.activityLevel] * calorie);
    }

    public boolean getLoseWeight() {
        return this.loseWeight;
    }

    public boolean getHasSetMaintain() {
        return this.hasSetMaintain;
    }

    public Gender getSex() {
        return this.sex;
    }

    /**
     * This is a function to check if it's an empty profile.
     */

    public boolean getIsSetup() {
        return this.isSetup;
    }
}
